param(
    [ValidateSet('Candidate','Published')][string]$Stage = 'Candidate',
    [string]$EvidencePath = 'docs/releases/v0.1.0-evidence.json'
)
$ErrorActionPreference = 'Stop'
Add-Type -AssemblyName System.IO.Compression.FileSystem
$repo = Split-Path $PSScriptRoot -Parent
Push-Location $repo
function Assert-Archive([string]$Path, $Manifest) {
    if ((Get-FileHash -LiteralPath $Path -Algorithm SHA256).Hash.ToLowerInvariant() -ne $Manifest.artifact.sha256) { throw 'Artifact SHA-256 mismatch.' }
    if ((Get-Item -LiteralPath $Path).Length -ne $Manifest.artifact.size) { throw 'Artifact size mismatch.' }
    $zip = [IO.Compression.ZipFile]::OpenRead((Resolve-Path -LiteralPath $Path))
    try {
        $reader = New-Object IO.StreamReader($zip.GetEntry('fabric.mod.json').Open())
        try { $metadata = $reader.ReadToEnd() | ConvertFrom-Json } finally { $reader.Dispose() }
        if ($metadata.version -ne $Manifest.source.version -or $metadata.depends.minecraft -ne '26.2' -or $metadata.depends.noammaddons -ne '1.3.5') { throw 'Wrong embedded release version/target/dependency.' }
        if ($metadata.name -ne 'Crit Addons') { throw 'Wrong feature identity.' }
        $reader = New-Object IO.StreamReader($zip.GetEntry('untitled.mixins.json').Open())
        try { $mixins = $reader.ReadToEnd() | ConvertFrom-Json } finally { $reader.Dispose() }
        foreach ($name in @('MixinCritKeyboard','MixinCritMouse','MixinCritWindow','MixinMinecraft','ActionUtilsAccessor','MixinCategoryPanel')) {
            if ($mixins.client -notcontains $name) { throw "Missing release mixin: $name" }
        }
        foreach ($entry in $zip.Entries) {
            if ($entry.FullName -match '/dev/|CritControlFixture|FixtureSession') { throw 'Development fixture leaked into release.' }
            if ($entry.FullName.EndsWith('.class')) {
                $stream = $entry.Open()
                try { $header = New-Object byte[] 8; if ($stream.Read($header,0,8) -ne 8 -or ($header[6] * 256 + $header[7]) -ne 69) { throw "Wrong Java class target: $($entry.FullName)" } } finally { $stream.Dispose() }
            }
        }
        foreach ($required in @('com/github/noamm9/untitled/crit/control/SessionCoordinator.class','com/github/noamm9/untitled/features/impl/critaddons/DungeonMapPopup.class')) {
            if (!$zip.GetEntry($required)) { throw "Missing production class: $required" }
        }
    } finally { $zip.Dispose() }
}
try {
    $manifest = Get-Content -LiteralPath $EvidencePath -Raw | ConvertFrom-Json
    if ($manifest.source.version -ne '0.1.0' -or $manifest.target.minecraft -ne '26.2' -or $manifest.target.java -ne 25) { throw 'Wrong candidate target.' }
    $source = (& git rev-parse "$($manifest.source.commit)^{commit}").Trim()
    if ($LASTEXITCODE -ne 0 -or $source -ne $manifest.source.commit) { throw 'Missing immutable source commit.' }
    $changed = & git diff --name-only $source -- src/main src/devControl src/controlTest build.gradle.kts gradle.properties settings.gradle.kts stonecutter.gradle.kts gradle versions/26.2/gradle.properties
    if ($changed) { throw "Build inputs differ from recorded source: $changed" }
    $properties = (& git show "${source}:gradle.properties") -join "`n"
    if ($properties -notmatch '(?m)^mod_version=0\.1\.0\s*$') { throw 'Source version mismatch.' }
    Assert-Archive $manifest.artifact.path $manifest
    if ($Stage -eq 'Published') {
        if (!$manifest.release.url -or !$manifest.release.assetUrl) { throw 'No published release evidence.' }
        $headers = @{ Accept = 'application/vnd.github+json'; 'User-Agent' = 'Crit-Addons-Release-Verification' }
        if ($env:GITHUB_TOKEN) { $headers.Authorization = "Bearer $env:GITHUB_TOKEN" }
        $release = Invoke-RestMethod -Uri 'https://api.github.com/repos/FateShop/Crit-Dungeons-V2/releases/tags/v0.1.0' -Headers $headers
        if ($release.draft -or $release.tag_name -ne 'v0.1.0' -or $release.html_url -ne $manifest.release.url) { throw 'Remote release mismatch.' }
        $refs = & git ls-remote origin 'refs/tags/v0.1.0' 'refs/tags/v0.1.0^{}'
        if ($LASTEXITCODE -ne 0 -or !$refs) { throw 'Remote tag missing.' }
        $resolved = if (@($refs).Count -gt 1) { @($refs)[-1] } else { @($refs)[0] }
        if (($resolved -split '\s+')[0] -ne $source) { throw 'Remote tag does not match candidate source.' }
        $asset = @($release.assets | Where-Object name -eq $manifest.artifact.name)
        if ($asset.Count -ne 1 -or $asset[0].browser_download_url -ne $manifest.release.assetUrl) { throw 'Remote asset missing/mismatched.' }
        $folder = Join-Path $repo 'build/release-verification'
        New-Item -ItemType Directory -Force -Path $folder | Out-Null
        $download = Join-Path $folder $manifest.artifact.name
        Invoke-WebRequest -UseBasicParsing -Uri $manifest.release.assetUrl -Headers $headers -OutFile $download
        Assert-Archive $download $manifest
        Write-Host 'Published tag and independently downloaded JAR verified.'
    }
    Write-Host "$Stage release evidence passed for $source."
} finally { Pop-Location }

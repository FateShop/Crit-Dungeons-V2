param(
    [string]$DependencyPath = "$env:APPDATA\PrismLauncher\instances\26.2\minecraft\mods\NoammAddons-1.3.5-26.2-cheat.jar",
    [string]$JdkHome = "$env:APPDATA\PrismLauncher\java\java-runtime-epsilon",
    [switch]$SkipBuild
)
$ErrorActionPreference = 'Stop'
$repo = Split-Path $PSScriptRoot -Parent
Push-Location $repo
try {
    $jdk = $JdkHome
    $java = Join-Path $jdk 'bin\java.exe'
    $javap = Join-Path $jdk 'bin\javap.exe'
    $version = (& $java --version | Out-String)
    if ($version -notmatch '(?m)^(openjdk|java) 25[.\s]') { throw 'JDK 25 is required.' }
    $expected = '6212888DDCE467FB0DA81BC3DF9E285C39867FC8AA9547BB4976EEC911F3F6FD'
    if ((Get-FileHash -LiteralPath $DependencyPath -Algorithm SHA256).Hash -ne $expected) { throw 'Exact installed Noamm dependency hash mismatch.' }
    $abi = (& $javap -p -classpath $DependencyPath com.github.noamm9.utils.ActionUtils com.github.noamm9.utils.dungeons.DungeonListener com.github.noamm9.utils.dungeons.DungeonPlayer com.github.noamm9.features.impl.dungeon.map.MapRenderer | Out-String)
    if ($LASTEXITCODE -ne 0) { throw 'Dependency ABI inspection failed.' }
    foreach ($contract in @('PriorityBlockingQueue<.*actionQueue', 'boolean running;', 'Object lock;', 'boolean isBlocked;', 'getDungeonStartTime\(', 'getDungeonEnded\(', 'boolean isDead\(', 'draw\(net.minecraft.client.gui.GuiGraphicsExtractor, boolean, boolean\)')) {
        if ($abi -notmatch $contract) { throw "Missing dependency ABI contract: $contract" }
    }
    $target = Get-Content versions/26.2/gradle.properties -Raw
    if ($target -notmatch '(?m)^noammaddons_version=1\.3\.5\s*$') { throw 'Wrong Noamm target version.' }
    $owners = Get-Content docs/implementation-ownership.md -Raw
    foreach ($owner in @('/root/impl_coordinator_sol', '/root/impl_map_sol', '/root/phase1_research')) {
        if (!$owners.Contains($owner)) { throw "Missing implementation/research ownership: $owner" }
    }
    $mixins = Get-Content src/main/resources/untitled.mixins.json -Raw | ConvertFrom-Json
    foreach ($name in @('MixinCritKeyboard','MixinCritMouse','MixinCritWindow','MixinMinecraft','ActionUtilsAccessor')) {
        if ($mixins.client -notcontains $name) { throw "Unregistered production mixin: $name" }
    }
    $controlFiles = Get-ChildItem src/main/kotlin/com/github/noamm9/untitled/crit/control -Filter '*.kt'
    foreach ($file in $controlFiles) {
        $code = Get-Content $file.FullName -Raw
        $code = [regex]::Replace($code, '(?s)/\*.*?\*/|(?m)//[^\r\n]*', '')
        if ($code -match 'ActionUtils\s*\.\s*reset\s*\(') { throw "Forbidden shared reset: $($file.Name)" }
    }
    foreach ($name in @('MixinCritKeyboard.java','MixinCritMouse.java','MixinCritWindow.java')) {
        $code = Get-Content "src/main/java/com/github/noamm9/untitled/mixins/$name" -Raw
        $code = [regex]::Replace($code, '(?s)/\*.*?\*/|(?m)//[^\r\n]*', '')
        if ($code -match '\bci\s*\.\s*cancel\s*\(|cancellable\s*=\s*true') { throw "Native input suppression: $name" }
    }
    if (!$SkipBuild) {
        $env:JAVA_HOME = $jdk
        & .\gradlew.bat :26.2:controlTest :26.2:build :26.2:verifyControlFixtureIsolation
        if ($LASTEXITCODE -ne 0) { throw '26.2 verification failed.' }
    }
    Write-Host 'Phase 1 automated gates passed. Live/floor evidence is recorded separately in docs/phase1-live-validation.md.'
} finally { Pop-Location }

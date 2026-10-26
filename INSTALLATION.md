# Installation and recovery

This source draft has not been validated as a fresh community install. Do not use the household APK as the generic release.

1. Build or obtain a release you trust. Enable ADB debugging on your own compatible Fire TV and approve the pairing prompt.
2. Inspect `launcher/install.sh` and `scripts/launcher.sh` before using them; they manage launcher activation. See `launcher/README.md` for upstream behavior. Do not run `scripts/all.sh` just to obtain the theme: it includes unrelated cleanup and filtering steps.
3. Back up any existing launcher configuration before replacing an installation. The app package is shared with upstream; signing identities must match to upgrade in place.
4. Open the launcher manually first. Check app discovery, arrow-key focus, app launch, Back, native search and settings before enabling Home interception.
5. Keep stock Amazon Home enabled. Preserve the original accessibility and alarm settings when changing launcher activation. Consult the upstream restore workflow and review its scope before running it.

## HDMI inputs

If Home switches the TV to the wrong HDMI input, check Fire TV Settings > Equipment Control > Manage Equipment > Fire TV > TV Input. Set the actual HDMI port. HDMI-CEC is a separate setting; do not automatically change it for every user.

## Scope

This is an Android/Fire TV launcher. LG Developer Mode does not make this APK a webOS app. No root, system partition changes or factory reset are required or appropriate.

## Preview APK: open manually first

For the upcoming Afterglow preview, use `dumbtv-afterglow-preview.apk` and its SHA256 file once v0.1.0-preview.2 is published. The older v0.1.0-preview.1 release still uses `dumbtv-viking-preview.apk`. Check the hash, then use your authorized ADB connection:

```sh
adb install -r dumbtv-afterglow-preview.apk
adb shell am start -n tv.dumbtv.home/.Home
```

These commands install/open the app without enabling Home interception. Stop if ADB reports UPDATE_INCOMPATIBLE: the existing launcher uses another signing key. Do not automatically uninstall it. Keep the current working setup, back up settings where supported and plan recovery before replacing it. The separately tested preview app was an isolated package; this downloadable APK uses the upstream `tv.dumbtv.home` package.

The default `launcher/install.sh` download path remains disabled during preview because its automatic Home activation has not yet been validated for this release. Its `--build` and `--undo` modes are inherited from upstream and still require careful review. Building locally generates a different signing identity unless you supply your own matching key.

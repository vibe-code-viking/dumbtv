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

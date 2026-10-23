# Optional local icon overrides

The theme works without downloaded icons. Installed app icons are the default, drawn in grayscale. A user can supply white transparent PNG logos locally if their use is permitted.

Put overrides in the launcher's own external files folder under `icons/<exact.package.id>.png`. The package ID must come from installed app metadata, not from its display name. Use a PNG up to 2 MiB with dimensions no larger than 4096 x 4096. Images are decoded to a maximum dimension near 256 pixels. Malformed, oversized and missing files fall back to the installed icon. Preserve aspect ratio. The small corner bounding box favors clear, simple marks.

On devices that permit ADB access to app external storage, copy the file using your verified device connection. Fire OS may restrict that folder; this draft has no on-TV file picker yet. Do not weaken storage protections to import an icon. Building private drawable resources is another developer option, but this draft intentionally loads overrides only from the local folder.

Restart the launcher after changing files so cached artwork refreshes. Override files are never uploaded automatically. See catalog.json for candidate source references, not a ready-to-download or permission-cleared asset list.

A download command is deliberately not included until each supported source and transformation is reviewed. The TV itself does not fetch remote logos.

## Reliable private build-time import

After obtaining a permitted local PNG and verifying its installed package ID:

```sh
python tools/import-icon.py --package com.example.streaming --file /path/to/permitted-logo.png
bash launcher/build.sh
```

This imports the file into an ignored drawable resource. The resulting private APK embeds the supplied artwork; verify terms before sharing that APK. The public source tree stays logo-free. The script never searches the internet or downloads assets. A new build is required to add or change a built-in override. Missing or malformed runtime overrides fall through to built-in overrides, then installed icons.

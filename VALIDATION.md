# Local validation

Afterglow 0.1.0-preview.2 rename candidate: strict API 25 compilation, resource and
DEX packaging, signing, manifest and local APK checksum, shell syntax, catalog
validation and README image links passed. The packaged image audit found only
Charcoal Smoke. No household logos or app selection are bundled. This candidate
still needs its own on-TV check; older test results below describe the preceding
community preview and are not new Afterglow test claims.

- Java compilation against Android API 25 with JDK 17, warnings treated as errors: passed.
- Android resource compilation/linking, D8 dex generation, ZIP alignment: passed.
- Catalog JSON parsing: passed.
- Snapshot privacy check for known household addresses/identifiers and credential/signing file types: passed.
- Confirmed no service-logo drawable resources or SVG masters are included.
- Default release download blocked until an Afterglow release is configured.

Not yet validated: activation/restore as the Home handler, runtime icon import under Fire OS storage restrictions, and upstream shell integration tests. The installed personal TV build has not been replaced.

Isolated on-device test (separate package, no Home interception): app discovery, initial Charcoal Smoke, Brand Fades -> Monochrome, background -> Black, persistence after force-stop/restart passed. Local importer header/dimension/package validation passed. Newly added build-time resource lookup compiles; public signed release and fresh activation/restore still pending.

Upstream release CI is not enabled for this source preview: it assumes the upstream release/checksum. Fork-specific automated validation remains pending. Catalog validation passed the local one-level check; deep YAML validation remains pending.

## Afterglow on-device check — 2026-10-26

Tested the isolated Afterglow preview on Amazon AFTKRT, Fire OS 8 / API 30. A fresh install exposed a missing legacy theme default that produced four columns; the candidate now defaults to Afterglow sizing, while retaining the old monochrome setting for compatibility. The corrected build showed five columns. Verified the smoke wallpaper and compact header, Afterglow settings title, smoke-to-black switching and restoration, 24-hour clock, saved preferences after process restart, remote focus movement, native Fire TV search opening, and Netflix launching from a tile. No crashes appeared in the checked runtime log. The public build uses installed grayscale icons and automatic app discovery; personal logo overrides and app lists are not bundled. The household launcher was not replaced. HOME interception/activation and Fire OS 6/7 hardware were not retested.

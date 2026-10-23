# Building

Use JDK 17 and Android SDK platform 25 plus build-tools 34.0.0. The upstream `launcher/build.sh` builds without Gradle; inspect it and `launcher/version.env` before use. On Linux/macOS set JAVA_HOME and ANDROID_HOME, then run:

```sh
bash launcher/build.sh
```

The upstream build script handles its own local signing key. Keep all signing material private; do not commit generated APKs or keys. Publish approved APKs as GitHub Release assets, not source files. Back up the release signing key privately so future upgrades can use the same identity. The household APK and its signing key are not included in this source draft.

For Windows, use Git Bash/WSL with a matching SDK toolchain. A portable native Windows build workflow and automated release signing remain follow-up work. The local draft was checked with platform 25, build-tools 34, JDK 17 and javac warnings treated as errors.

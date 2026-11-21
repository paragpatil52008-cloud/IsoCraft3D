# IsoCraft 3D — Android prototype

This is a lightweight Kotlin + Jetpack Compose Android project intended as the first installable engineering-graphics prototype. It uses a custom Canvas isometric renderer rather than a heavyweight 3D runtime, which is more suitable for testing on low-memory phones.

## Included and working in this starter
- Compose dashboard and bottom navigation
- Dimension controls for a cuboid (width, depth, height)
- Linked 2D front/top/side outline panels
- Interactive dimension-driven isometric cuboid rendering
- Visible / hidden / centerline display mode selection
- Wireframe toggle
- Beginner challenge progress state for the current app session
- Classroom and Pro concept screens
- `ProjectionEngine.kt` with reusable orthographic and isometric projection functions
- GitHub Actions workflow to build a debug APK in the cloud

## Important scope boundary
This is a working MVP/prototype, not yet a commercial CAD product. The current 3D renderer displays a parameterized cuboid; it is not a general-purpose mesh kernel. True Boolean CAD operations, STL/OBJ/DXF parsing/export, hidden-line removal for arbitrary meshes, Gemini-backed verification, ARCore plane anchoring, Room persistence, teacher/student cloud sync, and Google Play Billing are not implemented in this first build. They require separate modules, credentials/backend services, device testing, and Play Console configuration. Do not advertise those features as available until implemented and tested.

## Build using a phone only (cloud build)
1. Install/open a browser on the Android phone and create or sign in to a GitHub account.
2. Create a new **public or private repository** named `IsoCraft3D`.
3. Upload the contents of this ZIP into the repository root (not the ZIP file itself). GitHub mobile website: repository → Add file → Upload files. For many files, the GitHub website's desktop view or a browser-based upload client may be easier.
4. Commit the files to the `main` branch.
5. Open the repository → **Actions** → workflow **Build IsoCraft 3D APK** → **Run workflow**.
6. Wait for the green successful run. Open that run, scroll to **Artifacts**, and download `IsoCraft3D-debug-apk`.
7. Extract the downloaded ZIP on your phone. It contains `app-debug.apk`.
8. Tap the APK to install. Android may ask you to allow installs from that browser/file manager; only allow this for the file you built from your own repository. Revoke that permission afterward if desired.

## If the build fails
Open the failed Actions run and copy the first red error section. Share it so the project can be corrected. Do not guess at fixes from the final generic failure line.

## Requirements
- Android min SDK 24
- Compile/target SDK 35
- Java 17 in cloud build
- Gradle Android plugin 8.9.2
- Kotlin 2.0.21

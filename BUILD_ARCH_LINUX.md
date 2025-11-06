# Building Sparrow on Arch Linux

This guide provides specific instructions for building Sparrow Bitcoin Wallet on Arch Linux systems.

## System Information

This build was tested on:
- **OS**: Arch Linux
- **Kernel**: 6.17.5-arch1-1
- **Java**: OpenJDK 25.0.1
- **Gradle**: 9.1.0
- **Architecture**: x86_64

## Prerequisites

### Required Packages

Install the required development tools:

```bash
sudo pacman -S jdk-openjdk base-devel git
```

### Optional: RPM Support

If you want to build RPM packages on Arch Linux, install the RPM tools:

```bash
sudo pacman -S rpm-tools
```

Note: RPM packages are typically not used on Arch Linux, so this is optional.

## Cloning the Repository

Clone the repository with submodules:

```bash
git clone --recursive https://github.com/sparrowwallet/sparrow.git
cd sparrow
```

If updating an existing clone:

```bash
git pull --recurse-submodules
```

## Building

### Option 1: Build Application Image Only (Recommended for Arch)

The simplest approach on Arch Linux is to build just the application image without creating distribution packages:

```bash
./gradlew clean
./gradlew jpackageImage
```

This creates a standalone application at `build/jpackage/Sparrow/`.

### Option 2: Build with Installer Packages

If you have RPM tools installed and want to create both DEB and RPM packages:

```bash
./gradlew clean
./gradlew jpackage
```

### Known Issue: RPM Creation Failure

The `jpackage` task may fail with the error:
```
Error: Invalid or unsupported type: [rpm]
```

This occurs because:
1. Arch Linux doesn't natively use RPM packages
2. The RPM tools may not be installed
3. The `-PskipInstallers=true` flag doesn't skip Linux installers (only macOS)

**Solution**: Use `jpackageImage` instead of `jpackage`, or install RPM tools as shown above.

## Running the Built Application

After building with `jpackageImage`, run Sparrow with:

```bash
./build/jpackage/Sparrow/bin/Sparrow
```

## Running from Source (Development)

For development work, you can run Sparrow directly from source without building:

```bash
./sparrow
```

Java 22 or higher must be installed.

## Distribution Packages

### Creating a Tarball

To create a distributable tarball:

```bash
./gradlew packageTarDistribution
```

This creates `build/jpackage/sparrowwallet-2.3.1-x86_64.tar.gz`.

### Debian Package Only

The build creates both DEB and RPM packages by default. If you only need the DEB package (more common on systems that support DEB), the RPM failure won't affect the DEB creation. The DEB package will be available even if RPM creation fails.

## Build Artifacts

After a successful build, you'll find:

- **Application**: `build/jpackage/Sparrow/bin/Sparrow`
- **Libraries**: `build/jpackage/Sparrow/lib/`
- **Configuration**: `build/jpackage/Sparrow/conf/`
- **UDEV rules**: `build/jpackage/Sparrow/conf/udev/` (for hardware wallet support)

## Hardware Wallet Support

For hardware wallet support on Arch Linux, you may need to install UDEV rules:

```bash
sudo cp build/jpackage/Sparrow/conf/udev/* /etc/udev/rules.d/
sudo udevadm control --reload-rules
sudo udevadm trigger
```

Then replug your hardware wallet.

## Troubleshooting

### Build Fails with "Invalid or unsupported type: [rpm]"

Use `./gradlew jpackageImage` instead of `./gradlew jpackage`, or install RPM tools.

### Java Version Issues

Ensure you have Java 22 or higher:

```bash
java -version
```

If you have multiple Java versions, set the default:

```bash
sudo archlinux-java set java-25-openjdk
```

### Permission Errors

If you encounter permission errors when running the built application, check the permissions on the `build/jpackage/Sparrow/` directory.

## Clean Build

If you need to start fresh:

```bash
./gradlew clean
```

This removes all build artifacts and allows you to rebuild from scratch.

## See Also

- Main README: [README.md](README.md)
- Reproducible builds: [docs/reproducible.md](docs/reproducible.md)
- Project website: https://sparrowwallet.com

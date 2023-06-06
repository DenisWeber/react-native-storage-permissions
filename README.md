# react-native-storage-permissions

## Introduction

Package to ask for MANAGE_EXTERNAL_STORAGE permission, when need to access all files in an android phone in React Native, this is the only way to access and Manage All files in Android 11 (API level 30) or higher.

## Getting started

`$ npm install react-native-storage-permissions --save`

### Mostly automatic installation

`$ react-native link react-native-storage-permissions`

### Manual installation

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`

  - Add `import com.secullum.RNStoragePermissionsPackage;` to the imports at the top of the file
  - Add `new RNStoragePermissionsPackage()` to the list returned by the `getPackages()` method

2. Append the following lines to `android/settings.gradle`:

  ```
  include ':react-native-storage-permissions'
  project(':react-native-storage-permissions').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-storage-permissions/android')
  ```

3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:

  ```
      compile project(':react-native-storage-permissions')
  ```

## Usage

```javascript
import RNStoragePermissions from 'react-native-storage-permissions';

const hasStoragePermissions = await RNStoragePermissions.checkStoragePermissionsAsync();

const result = await RNStoragePermissions.requestStoragePermissionsAsync();
```

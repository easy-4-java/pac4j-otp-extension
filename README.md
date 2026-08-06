# pac4j-otp-extension

[English](./README.md) | [简体中文](./README.zh-CN.md)

[![Java](https://img.shields.io/badge/Java-17-orange)](https://github.com/easy-4-java/pac4j-otp-extension) [![License](https://img.shields.io/badge/license-Apache%202.0-green)](./LICENSE)

## Table of Contents

- [1. Project Overview](#1-project-overview)
- [2. Features & Status](#2-features--status)
- [3. Requirements & Compatibility](#3-requirements--compatibility)
- [4. Architecture & Modules](#4-architecture--modules)
- [5. Installation](#5-installation)
- [6. Quick Start](#6-quick-start)
- [7. Configuration](#7-configuration)
- [8. Core Usage / API](#8-core-usage--api)
- [9. Testing & Build](#9-testing--build)
- [10. Versioning & Branches](#10-versioning--branches)
- [11. Contributing & License](#11-contributing--license)

## 1. Project Overview

**pac4j-otp-extension** is an extension of the [pac4j](https://www.pac4j.org) security library that aims to add
One-Time Password (OTP) support, including HOTP (HMAC-based) and TOTP (time-based) variants, to pac4j-based
applications.

**Project status: early skeleton.** The current `1.0.x` branch provides the package skeleton and the
credential carrier type `org.pac4j.ext.otp.OTPToken`; the OTP generation/verification logic and the pac4j
`Authenticator` / `Client` wiring are not implemented yet.

What this project is:

| Is                                                                 | Is not                                                      |
| :----------------------------------------------------------------- | :---------------------------------------------------------- |
| A pac4j-side OTP credential model extension (started)              | An OTP server or token provisioning service                 |
| Designed to fit the pac4j `Credentials → Authenticator → Profile` flow | A standalone 2FA framework with its own UI/flow            |
| Built on pac4j 5.0.x (`pac4j-core`, `pac4j-config`, `pac4j-http`)  | A replacement for the upstream `pac4j-otp` module           |

Typical scenarios (planned):

| Scenario                           | Description                                        |
| :--------------------------------- | :------------------------------------------------- |
| Two-factor login                   | Verify a TOTP code produced by an authenticator app |
| API/HMAC challenge                 | HOTP-based request signing or challenge-response   |
| Replay protection                  | One-time use tokens in distributed systems         |

## 2. Features & Status

| Capability                                   | Status           | Notes                                                                  |
| :------------------------------------------- | :--------------- | :--------------------------------------------------------------------- |
| OTP credential carrier type (`OTPToken`)     | Skeleton         | Class exists in `org.pac4j.ext.otp`; no behavior implemented yet       |
| HOTP (counter-based) generation/verification | Not implemented  | Roadmap                                                               |
| TOTP (time-based) generation/verification    | Not implemented  | Roadmap                                                               |
| pac4j `Authenticator` / `Client` integration | Not implemented  | Roadmap                                                               |
| JUnit coverage                              | No real tests yet | Only a placeholder `TokenExample` exists in `src/test`                 |

## 3. Requirements & Compatibility

| Requirement | Version              |
| :---------- | :------------------- |
| JDK         | 8+                   |
| Maven       | 3.0+ (wrapper included) |
| pac4j       | 5.0.x                |

Version lines of the easy4j project:

| Branch        | JDK  | Version pattern | Notes                           |
| :------------ | :--- | :-------------- | :------------------------------ |
| `feature/1.0.x` | 8    | `1.0.x.*`       | This README, current branch     |
| `feature/2.0.x` | 17   | `2.0.x.*`       | JDK 17 line                     |
| `feature/3.0.x` | 21   | `3.0.x.*`       | JDK 21 line                     |

## 4. Architecture & Modules

```text
  HTTP request / token input
              |
              v
   pac4j  Credentials  (OTPToken - skeleton)
              |
              v
  Authenticator  (HOTP/TOTP validation - roadmap)
              |
              v
   UserProfile  (OTP profile - roadmap)
              |
              v
  authenticated session / rejection
```

Single-module Maven project (`jar` packaging):

| Module                  | Responsibility                                   |
| :---------------------- | :----------------------------------------------- |
| `pac4j-otp-extension`   | OTP credential model and (planned) pac4j wiring  |

Core types:

| Type                               | Description                                          |
| :--------------------------------- | :--------------------------------------------------- |
| `org.pac4j.ext.otp.OTPToken`       | OTP credential carrier (protected constructor, empty) |

## 5. Installation

Artifacts are published to the aliyun repository and GitHub Releases; they are **not** on Maven Central yet.

```xml
<dependency>
    <groupId>io.github.easy4j</groupId>
    <artifactId>pac4j-otp-extension</artifactId>
    <version>2.0.x.x.20260630-SNAPSHOT</version>
</dependency>
```

```groovy
implementation 'io.github.easy4j:pac4j-otp-extension:2.0.x.x.20260630-SNAPSHOT'
```

## 6. Quick Start

There is no runnable authentication flow yet — the type below is the only public API, and its constructor is
`protected` (following the pac4j credential convention, it is meant to be subclassed).

```java
// The only public type on this branch.
// OTPToken is an empty carrier: it does not generate or verify any code yet.
@SuppressWarnings("unused")
public class MyOtpToken extends OTPToken {
    protected MyOtpToken(String rawResponse) {
        super(rawResponse);
    }
}
```

Expected result: the code compiles against the dependency. No runtime OTP behavior is available on this branch.

## 7. Configuration

This library currently has no configuration options — there is no OTP generation/validation logic to configure.
Once HOTP/TOTP support lands, options such as algorithm (HmacSHA1/HmacSHA256), digit count, step/period and
counter seed are expected to follow the standard `AbstractOtpAuthenticator` style of the upstream pac4j module
(**Assumption**).

## 8. Core Usage / API

Public API surface on this branch:

| Member                                      | Notes                                          |
| :------------------------------------------ | :--------------------------------------------- |
| `OTPToken(String rawResponse)`              | `protected` constructor, `@SuppressWarnings("serial")` |
| `TokenExample` (in `src/test`)              | Placeholder `main` class, not a unit test      |

Third-party dependencies pulled in for future OTP work: `fastjson` (2.0.x), `commons-lang3` (3.20.x),
`commons-httpclient` (3.1) and `not-yet-commons-ssl` (used by some OTP implementations) — none of them is
exercised by code yet.

## 9. Testing & Build

```bash
./mvnw clean verify
```

- Maven wrapper (`mvnw`) is committed to the repository.
- JaCoCo is configured with a line-coverage rule of 90% (`haltOnFailure=false`).
- On this branch the coverage gate is effectively unenforced because no unit tests exist yet — this is a known
  gap that must be closed as OTP logic lands.

## 10. Versioning & Branches

| Branch        | JDK  | Version pattern | Maintenance                                     |
| :------------ | :--- | :-------------- | :---------------------------------------------- |
| `feature/1.0.x` | 8    | `1.0.x.*`       | Current; OTP implementation is planned here     |
| `feature/2.0.x` | 17   | `2.0.x.*`       | JDK 17 line                                     |
| `feature/3.0.x` | 21   | `3.0.x.*`       | JDK 21 line                                     |

Artifacts are distributed via the aliyun Maven repository and GitHub Releases. Use the branch matching your
JDK baseline; do not use a higher line's jar on an older JDK.

## 11. Contributing & License

Contributions (OTP algorithms, pac4j client wiring, tests) are welcome — please open an issue first to discuss
the design.

This project is licensed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).

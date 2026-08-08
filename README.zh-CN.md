# pac4j-otp-extension

[English](./README.md) | [简体中文](./README.zh-CN.md)

[![Java](https://img.shields.io/badge/Java-21-orange)](https://github.com/easy-4-java/pac4j-otp-extension) [![License](https://img.shields.io/badge/license-Apache%202.0-green)](./LICENSE)

pac4j-otp-extension 是 pac4j 安全库的扩展组件，目标是为基于 pac4j 的应用增加一次性密码（OTP）能力，包括 HOTP（基于计数器）与 TOTP（基于时间）两种形态。

## 目录

- [1. 项目概述](#1-项目概述)
- [2. 功能与状态](#2-功能与状态)
- [3. 环境要求与兼容性](#3-环境要求与兼容性)
- [4. 架构与模块](#4-架构与模块)
- [5. 安装](#5-安装)
- [6. 快速开始](#6-快速开始)
- [7. 配置](#7-配置)
- [8. 核心用法 / API](#8-核心用法--api)
- [9. 测试与构建](#9-测试与构建)
- [10. 版本线与分支](#10-版本线与分支)
- [11. 贡献与许可](#11-贡献与许可)

## 1. 项目概述

**pac4j-otp-extension** 是 [pac4j](https://www.pac4j.org) 安全库的扩展组件，目标是为基于 pac4j 的应用增加一次性密码（OTP）能力，包括 HOTP（基于计数器）与 TOTP（基于时间）两种形态。

**项目状态：早期骨架。** 当前 `1.0.x` 分支仅提供包结构与凭证载体类型 `org.pac4j.ext.otp.OTPToken`；OTP 生成/校验逻辑及 pac4j 的 `Authenticator` / `Client` 集成尚未实现。

它是什么 / 不是什么：

| 是                                                                 | 不是                                              |
| :----------------------------------------------------------------- | :------------------------------------------------ |
| pac4j 侧的 OTP 凭证模型扩展（已起步）                                | OTP 服务器或令牌分发服务                            |
| 设计上遵循 pac4j 的 `Credentials → Authenticator → Profile` 流程   | 自带界面与流程的独立 2FA 框架                       |
| 基于 pac4j 5.0.x（`pac4j-core`、`pac4j-config`、`pac4j-http`）构建 | 上游 `pac4j-otp` 模块的替代品                      |

典型场景（规划中）：

| 场景                   | 说明                                                  |
| :--------------------- | :---------------------------------------------------- |
| 双因素登录              | 校验认证器 App 生成的 TOTP 动态码                       |
| API / HMAC 挑战应答     | 基于 HOTP 的请求签名或挑战应答                          |
| 防重放                  | 分布式系统中的一次性令牌使用                            |

## 2. 功能与状态

| 能力                                       | 状态           | 说明                                                              |
| :----------------------------------------- | :------------- | :---------------------------------------------------------------- |
| OTP 凭证载体类型（`OTPToken`）               | 骨架           | 类已存在于 `org.pac4j.ext.otp`，尚未实现任何行为                   |
| HOTP（计数器型）生成与校验                   | 未实现         | 规划中                                                            |
| TOTP（时间型）生成与校验                     | 未实现         | 规划中                                                            |
| pac4j `Authenticator` / `Client` 集成       | 未实现         | 规划中                                                            |
| JUnit 覆盖率                               | 暂无真实测试   | `src/test` 中仅有一个占位类 `TokenExample`                         |

## 3. 环境要求与兼容性

| 要求   | 版本                 |
| :----- | :------------------- |
| JDK    | 8+                   |
| Maven  | 3.0+（已内置 wrapper）|
| pac4j  | 5.0.x                |

easy4j 项目的版本线：

| 分支           | JDK  | 版本模式   | 说明                           |
| :------------- | :--- | :--------- | :----------------------------- |
| `feature/1.0.x` | 8    | `1.0.x.*`  | 本文档对应分支                  |
| `feature/2.0.x` | 17   | `2.0.x.*`  | JDK 17 版本线                  |
| `feature/3.0.x` | 21   | `3.0.x.*`  | JDK 21 版本线                  |

## 4. 架构与模块

```text
  HTTP 请求 / 令牌输入
              |
              v
   pac4j  Credentials  (OTPToken - 骨架)
              |
              v
  Authenticator  (HOTP/TOTP 校验 - 规划中)
              |
              v
   UserProfile  (OTP 用户资料 - 规划中)
              |
              v
  认证成功会话 / 拒绝访问
```

单模块 Maven 项目（`jar` 打包）：

| 模块                 | 职责                                          |
| :------------------- | :-------------------------------------------- |
| `pac4j-otp-extension`| OTP 凭证模型及（规划中的）pac4j 集成           |

核心类型：

| 类型                              | 说明                                                |
| :-------------------------------- | :-------------------------------------------------- |
| `org.pac4j.ext.otp.OTPToken`      | OTP 凭证载体（protected 构造方法，暂无行为）          |

## 5. 安装

制品发布在阿里云私服与 GitHub Releases，**尚未发布到 Maven Central**。

```xml
<dependency>
    <groupId>io.github.easy4j</groupId>
    <artifactId>pac4j-otp-extension</artifactId>
    <version>3.0.x.x.20260630-SNAPSHOT</version>
</dependency>
```

```groovy
implementation 'io.github.easy4j:pac4j-otp-extension:3.0.x.x.20260630-SNAPSHOT'
```

## 6. 快速开始

当前分支尚无可运行的认证流程——下列类型是唯一的公开 API，其构造方法为 `protected`（遵循 pac4j 凭证类型惯例，供子类继承使用）。

```java
// 该分支唯一的公开类型。
// OTPToken 是空载体：目前既不生成也不校验任何动态码。
@SuppressWarnings("unused")
public class MyOtpToken extends OTPToken {
    protected MyOtpToken(String rawResponse) {
        super(rawResponse);
    }
}
```

预期结果：代码可针对该依赖正常编译。本分支暂无任何运行时 OTP 行为。

## 7. 配置

该库当前没有配置项——尚无 OTP 生成/校验逻辑可供配置。待 HOTP/TOTP 能力落地后，预计将按上游 pac4j 模块中 `AbstractOtpAuthenticator` 的风格提供算法（HmacSHA1/HmacSHA256）、位数、步长/周期、计数器种子等选项（**假设**）。

## 8. 核心用法 / API

本分支公开 API 一览：

| 成员                                       | 说明                                               |
| :----------------------------------------- | :------------------------------------------------- |
| `OTPToken(String rawResponse)`             | `protected` 构造方法，带 `@SuppressWarnings("serial")` |
| `TokenExample`（位于 `src/test`）           | 占位 `main` 类，不是单元测试                        |

为后续 OTP 工作引入的第三方依赖：`fastjson`（2.0.x）、`commons-lang3`（3.20.x）、`commons-httpclient`（3.1）与 `not-yet-commons-ssl`（部分 OTP 实现会用到）——目前均未被任何代码使用。

## 9. 测试与构建

```bash
./mvnw clean verify
```

- 仓库内置 Maven wrapper（`mvnw`）。
- 已配置 JaCoCo 行覆盖率 90% 门禁（`haltOnFailure=false`）。
- 本分支尚无单元测试，覆盖率门禁实际未生效——这是已知缺口，需随 OTP 逻辑的实现一并补齐。

## 10. 版本线与分支

| 分支           | JDK  | 版本模式   | 维护说明                                     |
| :------------- | :--- | :--------- | :------------------------------------------- |
| `feature/1.0.x` | 8    | `1.0.x.*`  | 当前分支；OTP 实现计划在此完成                 |
| `feature/2.0.x` | 17   | `2.0.x.*`  | JDK 17 版本线                                 |
| `feature/3.0.x` | 21   | `3.0.x.*`  | JDK 21 版本线                                 |

制品通过阿里云 Maven 私服与 GitHub Releases 分发。请按 JDK 基线选择对应分支，不要在高版本线产物上使用低版本 JDK。

## 11. 贡献与许可

欢迎贡献（OTP 算法、pac4j 客户端集成、测试）——请先通过 issue 讨论设计。

本项目基于 [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0) 许可。

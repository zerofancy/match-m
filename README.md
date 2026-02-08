# match-m - 连连看游戏

## 项目简介

match-m 是一个使用 Kotlin 和 Jetpack Compose 开发的连连看游戏，支持多平台运行（Android、桌面、Web）。该项目旨在提供一个美观、流畅的连连看游戏体验，同时展示 Compose Multiplatform 的跨平台能力。

## 功能特性

- **核心连连看游戏玩法**：经典的连连看游戏逻辑，支持相同麻将牌的匹配和消除
- **多难度级别**：提供简单、中等、困难三个难度级别，对应不同的游戏时间和挑战
- **游戏时间系统**：每个难度级别有不同的初始时间，消除麻将牌可以获得额外时间
- **多平台支持**：使用 Compose Multiplatform 技术，可在 Android、桌面和 Web 平台运行
- **美观的用户界面**：采用现代的 Material Design 3 设计语言，界面简洁美观
- **响应式布局**：适配不同屏幕尺寸，提供良好的游戏体验

## 技术栈

- **开发语言**：Kotlin
- **UI 框架**：Jetpack Compose
- **跨平台技术**：Compose Multiplatform
- **构建工具**：Gradle
- **依赖管理**：Gradle Version Catalog

## 安装和运行

### 前提条件

- JDK 11 或更高版本
- Android Studio（仅 Android 开发需要）
- 适当的 IDE（如 IntelliJ IDEA）或文本编辑器

### 运行方法

#### Android 平台

1. 使用 Android Studio 打开项目
2. 同步 Gradle 依赖
3. 运行 `composeApp` 模块到 Android 设备或模拟器

#### 桌面平台

1. 在项目根目录执行以下命令：
   ```bash
   ./gradlew :composeApp:run
   ```

#### Web 平台

1. 构建 Web 版本：
   ```bash
   ./gradlew :composeApp:wasmJsBrowserDistribution
   ```
2. 部署生成的文件（位于 `composeApp/build/distributions/` 目录）到 Web 服务器

## 游戏规则

1. **游戏目标**：在规定时间内消除所有麻将牌
2. **操作方法**：点击两个相同的麻将牌，如果它们之间可以通过不多于两条直线连接，则会被消除
3. **时间规则**：
   - 简单模式：初始 60 秒，每次消除获得 60 秒额外时间
   - 中等模式：初始 30 秒，每次消除获得 30 秒额外时间
   - 困难模式：初始 30 秒，每次消除获得 10 秒额外时间
4. **胜利条件**：在时间结束前消除所有麻将牌
5. **失败条件**：时间结束时仍有未消除的麻将牌

## 项目结构

```
match-m/
├── composeApp/                # 主要应用代码
│   ├── src/                   # 源代码目录
│   │   ├── androidMain/       # Android 平台特定代码
│   │   ├── commonMain/        # 共享代码
│   │   ├── desktopMain/       # 桌面平台特定代码
│   │   └── wasmJsMain/        # Web 平台特定代码
│   └── build.gradle.kts       # 模块构建配置
├── gradle/                    # Gradle 配置
│   ├── wrapper/               # Gradle Wrapper
│   └── libs.versions.toml     # 依赖版本管理
├── build.gradle.kts           # 项目构建配置
├── gradle.properties          # Gradle 属性
├── gradlew                    # Gradle Wrapper 脚本（Unix）
├── gradlew.bat                # Gradle Wrapper 脚本（Windows）
├── settings.gradle.kts        # 项目设置
└── README.md                  # 项目说明文件
```

### 主要文件说明

- **GameViewModel.kt**：游戏核心逻辑，包含游戏状态管理、麻将牌匹配算法等
- **GameScreen.kt**：游戏主界面，包含游戏区域和控制元素
- **MenuScreen.kt**：游戏主菜单，提供开始游戏、设置、关于等选项
- **SettingScreen.kt**：游戏设置界面，可调整难度级别
- **Difficulty.kt**：定义游戏难度级别

## 贡献指南

欢迎为项目做出贡献！以下是贡献的步骤：

1. Fork 项目仓库
2. 创建新的分支（`git checkout -b feature/your-feature`）
3. 提交更改（`git commit -m 'Add some feature'`）
4. 推送到分支（`git push origin feature/your-feature`）
5. 打开 Pull Request

## 许可证

本项目采用 MIT 许可证。详情请参阅 [LICENSE](LICENSE) 文件。

## 致谢

- [Jetpack Compose](https://developer.android.com/jetpack/compose) - 现代化的 UI 工具包
- [Compose Multiplatform](https://www.jetbrains.com/lp/compose-mpp/) - 跨平台 Compose 实现
- [Kotlin](https://kotlinlang.org/) - 现代化的 JVM 语言

## 联系方式

如有问题或建议，欢迎通过 GitHub Issues 与我们联系。
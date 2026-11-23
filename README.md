# Litematica Jade Bridge

在 Jade 提示框里查看 Litematica 投影方块的信息。

加载原理图后，把准星对准投影，就能看到对应方块的名称、图标、ID 和建造材料。方块名称跟随游戏语言，支持简体中文、繁体中文和英文。

Minecraft **26.3** · Fabric · 客户端模组

作者：**hanlax093** · 版本：**0.1.0**

[源码](https://github.com/HanLax93/litematica-jade-bridge) · [问题反馈](https://github.com/HanLax93/litematica-jade-bridge/issues)

## 安装

把 `litematica-jade-bridge-mc26.3-0.1.0.jar` 放进游戏实例的 `mods` 文件夹。更新时先移除旧版，不要安装带 `-sources` 的源码包。

需要同时安装 **Jade 和 Litematica**，以及 **Fabric API 和 MaLiLib**。服务器端无需安装本模组。

| 依赖 | 版本 |
| --- | --- |
| Minecraft | 26.3 |
| Java | 25+ |
| Fabric Loader | 0.19.5+ |
| [Jade](https://modrinth.com/mod/jade) | 26.3.5 Fabric |
| [Litematica](https://modrinth.com/mod/litematica) | 0.29.1 |
| [MaLiLib](https://modrinth.com/mod/malilib) | 0.30.2 |
| [Fabric API](https://modrinth.com/mod/fabric-api) | 0.160.3+，选择 Minecraft 26.3 对应版本 |

目前按以上版本适配，其他版本暂未验证。

## 设置

按 `M` 打开 Litematica 主菜单，进入 **Jade 桥接设置**。也可以从 Jade 的插件设置中找到 **Litematica Jade 桥接**，两处设置是同步的。

默认只显示名称、图标、投影标记、方块 ID、材料和来源。需要更多信息时，可以分别打开：

- **完成状态**：缺失、正确、错误方块或方块状态不符。
- **投影位置**：投影方块的坐标。
- **世界实际方块**：同一位置在真实世界中的方块。
- **方块状态**：含水、朝向、各侧连接、轴向等属性。

这四项默认关闭。退出设置界面时会自动保存，也可以对单个选项恢复默认值。

启用桥接后，默认隐藏 Litematica 的普通方块信息，避免出现两个提示框。这个选项也可以关闭，校验器错误提示和材料列表照常显示。

## 使用时留意

- 先在 Litematica 中加载原理图，并开启放置和投影渲染。检测范围会跟随渲染层设置，距离使用 Jade 的方块检测距离。
- 准星前方有更近的真实方块或实体时，优先显示它的信息；投影和真实方块的命中面重合时，可以查看投影的完成状态。
- 材料按 Litematica 的换算结果显示，例如双层台阶需要两个台阶。没有对应建造物品的状态会显示“无需物品”。
- 常见方块属性提供中文翻译，尚未翻译的属性会显示原名。

## 构建

需要 JDK 25。

Windows：

```powershell
.\gradlew.bat build
```

Linux / macOS：

```sh
sh gradlew build
```

产物在 `build/libs/`。运行开发客户端使用 `gradlew.bat runClient`（Linux / macOS 使用 `sh gradlew runClient`），游戏目录为项目下的 `run/`。

测试说明见 [TESTING.md](TESTING.md)。

## 许可证

[MIT](LICENSE)。Jade、Litematica 和其他依赖保留各自的许可证。

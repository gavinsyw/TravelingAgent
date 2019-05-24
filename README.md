# TravelingAgent
Project of Software Engineering

## Maintanence Log
* Added JiaoChengCode filefolder, containing some basic codes for frame and applet programing.
* 添加了模块(Modules)文件夹，开始写一些模块
* 添加了小组作业文档(Documents)文件夹
* 路线规划功能(LuXianGuiHua)已经可以接入，但是损失函数还在调试中
* 目前的项目结构大概为：主模块(app)， AndroidPileLayout模块(library)， ~~dialogplus模块(dialogplus)。其中dialogplus尚未完全整合到整个项目中(简直心累orz)~~
* （可能的）接下来的开发任务：
  * 完善Recommendation、Simulation（谢知晖）（尽快搞定转后端）
  * 接入服务端（王伟哲）
  * 继续设计后续页面，包括路线生成、报告、用户管理（蒋黄扉、陈浩平）
  * 优化算法（宋大佬）
  * 减少git分支，version1还行
* 为保持UI同一，可参考MaterialDesign
  * 模板库 <https://github.com/navasmdc/MaterialDesignLibrary>
  * 设计参考 <https://material.io/?tdsourcetag=s_pctim_aiomsg>

## Developer Guide
### 路线规划功能的开发者帮助
* 路线规划功能现在能够再指定酒店的情况下对旅游景点的顺序做10天的规划，可以设置的参数有：一天途径的旅游景点的数量（不能超过6）

* 路线规划功能包中包含关键类Sight，后续开发用到景点类的，可以借鉴此类，以免重复

* 路线规划函数Graph类中的journeySequence函数返回了一个Vector<Sight>，是一个按顺序排列的景点的向量，注意其中包含了酒店。
# TravelingAgent
Project of Software Engineering

## Maintanence Log
* Added JiaoChengCode filefolder, containing some basic codes for frame and applet programing.
* 添加了模块(Modules)文件夹，开始写一些模块
* 添加了小组作业文档(Documents)文件夹
* 路线规划功能(LuXianGuiHua)已经可以接入，但是损失函数还在调试中
* 目前的项目结构大概为：主模块(app)， PileLayout模块(library)。

## Developer Guide
### 路线规划功能的开发者帮助
* 路线规划功能现在能够再指定酒店的情况下对旅游景点的顺序做10天的规划，可以设置的参数有：一天途径的旅游景点的数量（不能超过6）

* 路线规划功能包中包含关键类Sight，后续开发用到景点类的，可以借鉴此类，以免重复

* 路线规划函数Graph类中的journeySequence函数返回了一个Vector<Sight>，是一个按顺序排列的景点的向量，注意其中包含了酒店。





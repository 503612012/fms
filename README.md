# OVEN SPRINGBOOT DEOM

## 安装步骤
#### 1. 下载代码
git clone https://github.com/503612012/demo.git
#### 2. 初始化数据
mysql加载demo.sql文件
#### 3. 编译代码
进入项目根目录执行：mvn clean compile package
#### 4. 启动工程
进入项目根目录执行：./demo.sh start
#### 5. 查看工程当前状态
进入项目根目录执行：./demo.sh status
#### 6. 查看日志
进入项目根目录执行：./demo.sh log
#### 7. 停止工程
进入项目根目录执行：./demo.sh stop

## TODO LIST
- [ ] 记住我功能
- [x] 接口请求限流器
- [x] 全局异常捕获
- [x] 加入一个图表
- [x] 动态定时任务
- [x] 加入一个队列
- [x] 退出再登录后台报错
- [ ] demo.sql脚本重新弄一份
- [x] 启动完成后初始化字典表
- [x] 工时增加备注信息
- [ ] 预支薪资模块
- [ ] 网络卡的时候重复点击录入两份的现象
- [x] 一个员工一个工地一天只能录入一次，不是一个员工一天只能录入一次
- [x] 薪资发放模块增加工地查询条件
- [ ] 增加入账模块
- [ ] 系统设置菜单放到最下边
- [x] 发薪时时薪可修改，增加备注信息
- [ ] 工时管理和发薪记录模块的备注字段要不要展示
- [ ] 进入录入工时页面后，若没有查看员工薪资权限，则提示不能录入
- [ ] 所有的删除改成更改删除状态
- [ ] 考虑增加定时任务删除多余数据
- [x] 发薪时金额可修改，若修改了薪资，则备注必须填写
- [x] 修改发薪金额增加权限
- [ ] 录入工时时，若没有查看薪资权限则不自动填充薪资
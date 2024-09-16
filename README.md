
# 果汁

## 使用

> 默认启动局域网服务地址  
> 127.0.0.1:8080

## 介绍
> 果汁是一个跨平台启动客户端
> 运行Nebula语言  
> 拥有良好的语法功能  
> 帮助你更快更便捷开发网站等服务

### 使用说明
```
启动后会生成juiceData文件夹
请仔细阅读README.md文档进行使用
启动成功后就能访问127.0.0.1:8080进行查看
```

## 手机目录
Documents/juiceData

## wn词库

```
<h1>
<?n
$全局变量 版本$
?>
</h1>
```

## n词库

```
头部
赋予值:内容

Main
触发执行：%赋予值%
```

## 初始文件

### 程序内置文档位置
juiceData/juice.md

### 默认启动
**127.0.0.1:8080**

### 程序系统重要文件

#### 程序启动执行
juiceData/system/start.n

### 程序网页重要文件

#### 随着系统跟随启动的目录文件
juiceData/web/private/system/start.n

#### 路由词库
juiceData/web/private/system/router.n

## 开发讨论
QQ群：927467925

## Github

```
https://github.com/cjxpj/juice_WebNebulaLanguage
```

## Gitee

```
https://gitee.com/cjxpj/juice_WebNebulaLanguage
```

## Nebula

## 介绍
> 基于Golang语言开发  
> 专注WEB站点开发  
> 快速成型项目

### 认识文件分类

| 名称 | 目录 |
| ---- | ---- |
| 储存目录 | database |
| 资源目录 | private |
| 词库目录 | public |

```
储存目录用于读写，资源目录用于存放重要资源跟词库，词库目录用于被访问执行公开的数据。
```

### 执行

> 词库支持头部在没有触发词情况下执行哦！

```
头部

Main
尾巴
```

### JSON框

```
JSON>赋予值={}
a=b
a->a=b
<JSON
```

```
JSON>[]
0=a
1=b
<JSON
```

### 文本框

> 帮助你更方便的多行文本

```
文本>赋予值=\n
内
容
<文本
```

```
文本>\n
内
容
<文本
```

### 函数框

> 函数框可以帮助你在运行词库时候动态执行词库  
> 可以减少词库回调需求  
> 触发可以留空  
> 触发留空后就会直接运行  
> 函数框也支持获取参数跟括号

```
函数>赋予值=触发
a
<函数
$函数 赋予值 触发$
```

```
函数>赋予值
a
<函数
$函数 赋予值$
```

#### 调用

```
函数>赋予值
$延迟 [1000*5]$
$打印 结束$
<函数
$打印 开始$
$异步函数 赋予值$
```

### 循环框

更方便的循环  
可以使用>终止  
也能修改当前循环次数**i:1**

```
循环>i=9
%i%
<循环
```

留空死循环为32767次

```
循环>i
%i%
<循环
```


遍历JSON无法修改当前循环次数  
但是可以使用>终止

```
循环>i,ii=["a","b"]
%i%=%ii%
<循环
```

可以用在循环框中退出循环

```
循环>i=5
%i%
如果:%i%>2
结束
>终止
<循环
```

循环中支持
```
>跳过
>终止循环
>终止
```

### 整合包

这个相当于面对象功能，后续打算支持New到局部变量中。

```

整合包=>我的包

[函数]设置变量 .*
$%自己 变量 %参数1%$

[函数]读取变量
$%自己 变量$

<=整合包

Main
// 执行整合包函数
$.我的包 设置变量 成功$
$.我的包 读取变量$
// 整合包中获取变量
$%我的包 变量$
```

### 编译框

n文件可以用于词行换行
```
Main
<?n

a

b

c
?>
```

wn文件用于执行词库

```
<h1>
<?n
%版本%
?>
</h1>
```

### 加密词库

返回true/false

```
$加密词库 private/dic.n$
```

生成出来的词库会在当前程序的encode文件夹中

### 大小写字母

```
$大写字母 a$
$小写字母 A$
```

### 读取数据

用于读取文件全部文本

```
$读文件 database/a.txt 默认值$
```

读是在一个文件储存多个数据的方法  
只会写入到database文件夹下

```
$读 路径 键 默认值$
```

可以获取json列表

```
$读 文件路径$
```

### 写入数据

用于完全写入文本数据

```
$写文件 database/a.txt 数据$
```

写是在一个文件储存多个数据的方法  
只会写入到database文件夹下

```
$写 路径 键 数据$
```

### 回调

内部可以放在触发下

支持缩写[L]

```
$回调 试试$

[内部]试试
成功
```

### 赋予值

都支持多字符赋予值文本

```
:$: 执行函数
: 支持多字符
+: 拼接跟加值
-: 减少数值
:: 绝对文本
:%: 只读取变量
```

特殊功能

示例
```
a:{"a":"b"}->a
%a%
```

示例
```
a:{"a":"b"}!->A
%a%
```

示例
```
a:false?:错误
%a%
```

套娃函数

```
:($):
```

示例
```
a:($):$读 b a $读 b a ok;;$写 a a 成功;$读 a a $读 a a ok;;
%a%
```

简便文本框配合编译框使用

```
Main
<?n
这是文本:"""
cxk
cxk


cxk
找到cxk


然后cxk
"""
?>
%这是文本%
```

用于框选词库换行的
```
<?n
?>
```

### 变量

读取变量

```
a:你好
%a%
```

取反变量

```
a:false
%!a%
```

### 注释

```
// 注释
/*
多行注释
*/
```

注释执行他跟**赋予值**一样  
但是他不会产生任何数据

```
#:执行
```

### 主机信息

Android无法使用，需要另外方法获取。

```
$主机 CPU$
$主机 CPU信息$
$主机 内存$
$主机 磁盘$
$主机 网络$
```

### 数字格式化

默认f可填**b/e/E/f/g/G**

```
$数字格式化 1.1201 2 f$
```

### Js

,来分割参数
从参数0开始
a,b,c

```
a:注入文本
Js>返回赋予值=%a%,b
参数0+参数1
<Js
%返回赋予值%
```

### Lua

,来分割参数
a,b,c

```
a:注入文本
Lua>返回赋予值=%a%,b
function main(txt,txt2)
return txt..txt2
end
<Lua
%返回赋予值%
```

### MYSQL

后续考虑支持多线程，如有特殊需求请及时反馈。

```
// ()括号可以移除默认本地
$MYSQL连接 user:pass@(localhost:1234)/dbname$
$MYSQL查询 [值]$
$MYSQL执行 [值]$
// 记得随手关闭
$MYSQL断开$
```

### 时间

```
%时间戳%
%毫秒时间戳%
%纳秒时间戳%
%时间yyyy%
```

### 函数

支持缩写[F]

```
a:b
$ok$
$z%b%$
// 只有这个可以运行
$z%a%$


[函数]ok
b

// 可以指定读取变量

[函数]z%a%
%参数0%
```

变量传出写法

```
Main
$测试 ->成功$
%a%
%b%
%c%
$函数 动态函数$

[F]测试 ->.*->a,b,c,动态函数
a:1
b:2
c:3
函数>动态函数
4
<函数
%参数1%
！
```

### 随机数

```
%随机数1-100%
```

```
$随机数 1 100$
```

### 特殊符号

| 编号 | 符号 |
| ---- | ---- |
| 0 | $ |
| 1 | % |
| 2 | : |
| 3 | 空格 |
| 4 | \t |
| 5 | \n |
| 6 | ; |
| 7 | [ |
| 8 | ] |
| 9 | _ |

> 可以让你在函数中，不用写多余赋予值，然后用变量传入。

### 日志

```
// 成功
$日志 内容$
$日志 内容 成功$
// 失败
$日志 内容 失败$
```

### ZIP

```
$ZIP 压缩 database/文件夹 database/a.zip$
```

```
$ZIP 解压 database/a.zip database/文件夹$
```

### 文件大小

```
$文件大小 database/file.txt$
```

### 文件夹大小

```
$文件夹大小 database$
```

### 捕获

```
// 获取准备输出的内容
$捕获输出$
// 清空准备输出的内容并返回
$拦截输出$
```

### 计算

> 谁天生是数学天才？  
> 支持运算符号
```
加 +
减 -
乘 *
除 /
```

```
[2*(1+1)]
```

```
$计算 1 加 1$
$计算 1 + 1$
$计算 1 减 1$
$计算 1 - 1$
$计算 1 乘 1$
$计算 1 * 1$
$计算 1 除 1$
$计算 1 / 1$
$计算 1 除余 1$
$计算 1 % 1$
$计算 1 按位或 1$
$计算 1 | 1$
$计算 1 << 1$
$计算 1 左移 1$
$计算 1 >> 1$
$计算 1 右移 1$
$计算 1 根号 1$// 平方根
$计算 1 sqrt 1$
$计算 1 四舍五入 1$
```

### 启动服务器

可以让词库开启局域网服务  
会返回启动的链接  
都是异步启动支持启动多个

目录可留空  
目录是用于多个项目分类启动的

```
$启动服务器 localhost:8080$
$启动服务器 localhost:8080 目录$
```

#### 启动后数据
```
$全局变量 访问数据$
```

GET返回示例
```json
{
    "路径":"/api.n",
    "来源":"GET",
    "请求头":{"Accept-Encoding":["gzip"],"User-Agent":["Nebula-Client/1.0"]},
    "IP":"127.0.0.1",
    "Host":"127.0.0.1:8080"
}
```

有GET参数时候会出现
```json
{
    "GET":{"a":["a"]}
}
```

POST返回示例
```json
{
    "路径":"/api.n",
    "来源":"POST",
    "请求头":{"Accept-Encoding":["gzip"],"User-Agent":["Nebula-Client/1.0"]},
    "IP":"127.0.0.1",
    "Host":"127.0.0.1:8080"
}
```

有POST参数时候会出现
```json
{
    "POST":{"a":["a"]}
}
```

有POST文件时候出现

| 名称 | 说明 |
| :--: | :--: |
| file | 表单 |
| name | 文件名 |
| size | 大小 |
| data | 数据 |

data文件内容为了避免乱码数据出错采用了Base64编码，需要自己解码。

```json
{
    "POSTFile":{"file":[{"name":"a.txt","size":1,"data":"YQ=="}]}
}
```


#### WebSocket

```
WebSocket::true
$启动服务器 localhost:8080$
```

```
// 在被动WS中使用
$WS返回 内容$
// 记录后可以通过发送控制
$WS记录 命名$
```


#### 输出类型

在启动服务器后被访问的词库提供输出类型的方法

```
$全局变量 输出类型 text$
```


#### 输出头部

设置后会在输出时候进行

```
$全局变量 输出头部 {"Location":"https://cjxpj.com"}$
```


#### COOKIE

设置后会在输出时候进行  
《**禁用JS**》默认false，禁用后无法让JavaScript读取，仅限在域名访问中传输。  
《**存活**》默认0，秒为单位。

```
a:"""
[
    {
        "命名": "id",
        "数据": "123456",
        "路径": "/",
        "禁止JS": false,
        "存活": 3600,
    }
]
"""
$全局变量 COOKIE %a%$
```


**目录结构**

连接成功
> private/websocket/connect.n
> 返回消息会打印到日志

消息接收
> private/websocket/msg.n
> 返回消息时候会自动发送一次WS

断开连接
> private/websocket/close.n
> 返回消息会打印到日志
> 触发Main|Error

### WS

WS连接配合异步使用

```
函数>函数框变量名
$打印 触发：%触发词%$
<函数
$WS连接 命名 ws链接 函数框变量名$
%报错%
```

```
$WS发送 命名 文本$
$WS连接断开 命名$
```

### 启动服务器挂载到内网穿透

仅限启动一个

```
$Ngrok token$
$Ngrok token 目录$
```

示例，这是我提供的免费渠道名额有限，当然你可以选择自己去Ngrok官方注册后填入你的token进行使用。
```
$Ngrok$
%报错%
```

会返回链接

### 中文转拼音

可以获取多音字，数字代表声调。

```
$中文转拼音 了$
```

返回示例

```json
[["le","lia3o","lia4o"]]
```

### 文件后缀

他可以从文件路径中取出最后的文件后缀

```
$文件后缀 a/a.txt$
```

### 访问

header可以留空

```
$访问 url header$
$访问POST url data header$
```

### 通信

先记录后操作

```
$通信记录 URL$
$通信GET$
$通信POST data$
$通信POST文件 form name data$
$通信POST文件 name data$
$通信头部 json/string$
$通信发包$
// 这是获取全部信息，包括准备发送的数据都在内。
$通信取出$
// 这是只获取结果
$通信取出结果$
```

上传文件  
form是表单name文件名字data是数据
```
$通信POST文件 form name data$
```

直接填名字默认表单跟文件都为同一个名字

```
$通信POST文件 name data$
```

### 函数列表
```
$函数列表$
```

### 随机文本

随机一个字符返回

```
$随机文本 abc$
```

随机一个文本组返回

```
$随机文本 , a,b,c$
```

### 绘图

字体在内部文件夹的字体文件夹内  
一个绘图函数中仅支持一个创建

| 需求 | 参数 | 所需 |
| ---- | ---- | ---- |
| 创建 | 1/2 | img:Base64\|x/y |
| 二维码 | 1 | string |
| 颜色 | 3/4 | R/G/B/A |
| 画字 | 1/3 | string/x/y |
| 字体 | 1 | file |
| 大小 | 1 | string:int |
| 伽马值 | 1 | string:int |
| 模糊 | 1 | string:int |
| 锐化 | 1 | string:int |
| 对比度 | 1 | string:int |
| 亮度 | 1 | string:int |
| 饱和度 | 1 | string:int |
| 画圆 | 2 | x/y |
| 画方 | 4 | x/y/w宽/h高 |
| 画线 | 4 | x0/y0/x1/y1 |
| 画虚线 | 6 | x0/y0/x1/y1/l长/s间隔 |
| 贴图 | 3/5/6 | img:Base64/x/y/w宽/h高/0.1~1透明度 |
| 插图 | 1/2 | times/img:Base64 |
| 输出 | 1 | gif/png/jpg/jpeg/信息 |

| 重构需求 | 参数 | 所需 |
| ---- | ---- | ---- |
| 圆形 | 0/1 | size |
| 圆角矩形 | 2/4 | x/y/dx/dy |
| 旋转 | 0/1 | rotate0~360 |

#### 重构json示例结构

```json
{
    "需求": "重构",
    "参数": ["旋转","180"]
}
```

#### 示例仅供参考

```
$全局变量 输出类型 image/png$
文本>画=
[
    {
        "需求": "颜色",
        "参数": ["255","255","255","255"]
    },
    {
        "需求": "创建",
        "参数": ["380","520"]
    },
    {
        "需求": "颜色",
        "参数": ["0","0","0","255"]
    },
    {
        "需求": "大小",
        "参数": ["25"]
    },
    {
        "需求": "画字",
        "参数": ["酒乐文游","135","80"]
    },
    {
        "需求": "颜色",
        "参数": ["255","0","0","128"]
    },
    {
        "需求": "画方",
        "参数": ["200","200","250","250"]
    },
    {
        "需求": "输出",
        "参数": ["png"]
    }
]
<文本
$绘图 %画%$
```

### 变量控制

全局变量就是在回调时候还在的变量  
不是全局的在执行完毕后就没了

```
$线程变量 键 值$
$全局变量 键 值$
$变量 键 值$
```

这个返回a因为键被锁了

```
键:a
$锁变量 键$
键:b
%键%
```

特殊变量功能，在每次读取会生成1开始数值往上的唯一数值，相当于自增。

```
$线程变量 _UID_$
$全局变量 _UID_$
$变量 _UID_$
```

示例
```
20个唯一uid
循环>i=20
,$线程变量 _UID_$
<循环
```

### 判断值和空值

| 判断 |
| ---- |
| null |
| nil |
| {} |
| [] |
| false |
| 空 |
| NaN |
| undefined |
| 留空 |


判断到空返回true
```
// 返回bool类型数据
$判断空值 null$
```

没判断到空返回true
```
$判断值 null$
```

### 跳行

是在词条局部内跳行  
支持读取变量

```
>跳行+1
```

```
>跳行-1
```

### 字符切片

```
// 返回[]json
$字符切片 内容$
```


### 判断

可以用英语缩写  
如果尾是不会终止往下执行的

| 中文 | 英文 |
| ---- | ---- |
| 如果 | if |
| 如果尾 | end |
| 返回如果尾 | else |

#### 返回如果


支持两个方法

```
a:2
如果:a==0
yes
否则如果:a==1
no
elif:a==2
Good
否则
error
```

#### 支持逻辑

```
和 &
或 |
```

#### 支持判断

```
大于等于 >=
大于 >
小于等于 <=
小于 <
等于 ==
不等于 !=
混合判断 ()
长度判断 !
数组是否存在字符 in
不等于正则 ~
正则 ~=
```

```
如果:\d+~=123
成功
否则
失败
```

**返回：成功**

```
如果:\d+~123
成功
否则
失败
```

**返回：失败**

```
如果:["a"] in a
成功
否则
失败
```

**返回：成功**

```
如果:aaa!bbb
成功
否则
失败
```

**返回：成功**

```
如果:a==a|(a==a&a==a)
ok
如果尾
如果:false
a
返回
如果尾
b
```

**返回：okb**

### 判断框

```
如果>a==a
ok
>否则
no
<如果
```

**支持套娃判断**

```
如果>a==b
    ok
>否则如果:a==a
    如果>a==a
        ok
    >否则
        no
    <如果
<如果
```

支持两种判断内方法

```
>跳过
>终止
```

### 终止程序

**结束程序并且把准备输出内容打印到日志**

```
$STOP$
```

### 替换

| 需求 | 内容 | 替换| 成为 | 次数 |
| ---- | ---- | ---- | ---- | ---- |
| 替换 | 11234 | 1 | 2 | 1 |

返回21234
次数留空为替换全部

```
$替换 替换内容 需要替换 替换后$
```

特殊用法

```
函数>a
如果:%触发词%==2
停止
否则
%触发词%
<函数
$替换 AAA A %a%$
```

#### 正则替换

```
$正则替换 文本 正则 替换后$
```

特殊用法

```
函数>a=1
>%触发词%<
<函数
$正则替换 A1A123A \d+ %a%$
```

### 下载文件

| 需求 | 目录 |
| ---- | ---- |
| 下载文件 | database |

```
$下载文件 url a.txt$
```

### 删除文件

| 需求 | 目录 |
| ---- | ---- |
| 删除文件 | database |

```
$删除文件 database/a.txt$
```

### 删除文件夹

```
$删除文件夹 database/缓存$
```

### 回调词库

> 异步词库可以配合延迟做调用

```
$执行词库 词库文本 触发 独立/继承/继承函数/互通$
$执行网页词库 词库文本$
```

**独立**
默认此方法，可以留空。
```
[指定执行]不会拥有局部[变量]和[函数]
```

**继承**
```
[指定执行]的词库会继承[变量]和[函数]
```

**继承函数**
```
[指定执行]的词库会继承[函数]
```


**互通**
```
会继承[函数]
互通[变量]
[指定执行]完的词库中变量可以影响到[执行]
```

### SMTP邮件

```
$邮件 链接 端口 账号 密码 目标账号 发送头部$
```

发送QQ邮箱样板
```
文本>发送=%val5%
Subject:标题
From:小啤酒 <2960965389@qq.com>
成功
<文本
$邮件 smtp.qq.com 587 2960965389@qq.com xxxxx 2960965389@qq.com %发送%$
```

### 去除多余符号

```
$去除左右 文本 符号$
$去除左 文本 符号$
$去除右 文本 符号$
```

### AES

key范围
16/24/32

```
$AES加密 CBC/CFB key 内容$
$AES解密 CBC/CFB key 内容$
```

### MD5编码

```
$MD5编码 内容$
```

### basc64

```
$B64编码 内容$
$B64解码 内容$
```

### url

> URL链接编码就是自动识别get参数自动编码  
> 不会影响到链接  
> URL编码是完全编码

```
$URL编码 内容$
$URL解码 内容$
$URL链接编码 url$
$URL链接解码 url$
```

### 存在文件判断

```
$存在文件 database/a.txt$
```

### 存在文件夹判断

```
$存在文件夹 database$
```

返回true或者false

### 文件列表

文件夹留空为站点目录

```
$文件列表 文件 database$
$文件列表 文件夹 database$
$文件列表 全部 database$
```

示例
```
$文件列表 文件夹$
```
返回
```
["database","private","public"]
```

### JSON

不会返回数据  
记录不是json会报错  
配合判断用

```
$JSON记录 {}$
$JSON取 [键]$
// 存可以改成存字只存文本数据
$JSON存 [键] [值]$
$JSON存字 [键] [值]$
// 可以直接通过多层键设置值
$JSON存 [值] [键] [键]$
// 这是给元素追加用的
$JSON追加 [值]$
$JSON存在 [键]$
$JSON删 [键]$
```

快捷方法

```
$JSON追加 [] [值]$
```

#### 有数据数据函数

| 需求 | 返回 |
| ---- | ---- |
| 解析 | string |
| 判断 | true/false |
| 长度 | int |
| 取出 | json/[]json/string |

```
// 快捷解析支持->批量解析
$JSON解析 {} 键->键$
$JSON判断 {}$
$JSON长度$
$JSON取出$
```

快捷方法

```
$JSON长度 {}$
```

#### JSON美化

1代表缩进空格数量，默认就是1可以留空。

```
$JSON美化 {}$
$JSON美化 {} 1$
```

### 长度

**文本长度是单字符算作1单位**

```
$文本长度 内容$
$长度 内容$
```

### 正则

正则匹配内容返回json

```
$正则 正则 内容$
```

正则匹配只会返回true/false

```
$正则匹配 正则 内容$
```

### 截取

```
$截取 abcd 1 2$
```

### 取中间

```
$取中间 abcd a c$
```

### 字符定位

> 没有找到会返回-1

```
$查找字 内容 查找内容$
```

### 延迟

> 毫秒单位

```
// 一秒延迟示例
$延迟 [1000*1]$
```

### HTML编码

```
$HTML编码 内容$
$HTML解码 内容$
```

### HTML解析

```
a:"""
<!DOCTYPE html>
<html>
    <head>
        <title>标题</title>
    </head>
    <body>
        <h1>文本</h1>
        <p>文本2</p>
    </body>
</html>
"""
$HTML解析 %a% html head title$
```

### MD转HTML

> 把MD文本转为HTML显示

```
$MD转HTML 内容$
```

### 系统

> 值留空为读取

```
$系统 键 值$
```

### 引入

可以让内部跟函数

#### private/dic/a.n

```

[函数]a
成功

```

#### 运行的词库
```
#引入=dic,a
$a$
```

### 插入词条

可以让文本直接插入执行

#### private/dic/a.n
```
ok

[内部]a
成功
```

#### 运行的词库
```
[插入dic/a.n]
$回调 a$
```

### 终止

> 这里只会输出一个a

```
a
>终止
a
```

### 分割

```
$分割 , a,b,c$
```

分割范围

```
$分割 , a,b,c 2$
```

### 时间戳格式化时间

```
$时间戳格式化时间 %时间戳% s秒$
```

### 终端

执行成功报错返回null

```
$终端 python test.py$
%报错%
```

### DLL

跟程序在同一个目录进行加载

```
$加载动态库 命名 test.dll$
```

回调dll的函数func字符串传参a,b,c

```
$回调动态库 命名 func a b c$
```

### 排序

true/false代表正反排序

```
$排序 键 数据 true/false$
```

使用示例

```
文本>z=
[
{"data":"100","key":"啤酒"},
{"data":"500","key":"兄弟"},
{"data":"500","key":"兄弟"},
{"data":"50","key":"你好香"}
]
<文本
$排序 data %z% true$
```

### 重命名移动

返回true/false
成功返回true
```
$重命名 database database2$
```

### 复制粘贴

返回true/false
成功返回true
```
$复制粘贴 database database2$
```

### 编码解码


| 支持编码 |
| :--: |
| GBK |
| 二进制 |
| 十六进制 |
| HZGB2312 |
| GB18030 |
| ASCII |
| ISO-8859-1 |

```
$编码 二进制 你好$
$解码 二进制 111001001011110110100000111001011010010110111101$
```


在这里,记录自己创建博客当中遇到的问题，以及实现等。

在这里,对表的结构进行,分析一个



首先是管理员表

管理员拥有 用户名,密码是否可见?(一般不可见,通过JWT加密),除此之外还有创建时间和最后的登录时间(用于记录管理的最后登录时间)

目录结构表,创建的时候,基于生成问题,目录都是动态生成的因此需要一张

文章表,通过什么形式存储在文章表当中



在这里列出所有拥有的表(以后可能会增加)

1. 管理员表**ms_admin**(用户的用户名以及密码,用于设计等)

2. 管理员权限表**ms_admin_permission**(在这里管理用户的权限)

3. 文章表**ms_article**(在这里有每一篇文章的id,日期,标题等,以及评论总数)
4. 文章内容表,**ms_article_body**这里是文章的内容表
5. 文章标签表**ms_article_tag**(在这里管理文章的标签)
6. 文章目录表**ms_category**(这里的static代表文章结构,后续会改)
7. 评论表 **ms_comment**(评论,包含了相关的uuid)
8. 用户权限表**ms_permission**（这里定义了权限功能)
9. 系统log表**ms_sys_log**(这里是系统的log)
10. 用户表**ms_sys_user**这里是用户的
11. 文章目录**ms_tag**这里是文章目录

![image-20221201160807867](allPicture/image-20221201160807867.png)



一些注意点

```
dependencyManagement
```

这个博客是一个**单体项目**,但是如果说拥有很多个项目,每个都需要指定版本号



回忆某个注释的作用

**@Configuration**,

Configuration配置注释,一般用来在里面初始化一些配置类

**configuration**和**component**的区别在于,configuration只能生成一个对象,component则有多个对象





**@SpringBootApplication**这个注解,用于标记这是一个主程序,是一个springboot应用





在Pojo中的常用注释

```java
常用的几个注解：
@Data ： 注在类上，提供类的get、set、equals、hashCode、canEqual、toString方法
@AllArgsConstructor ： 注在类上，提供类的全参构造
@NoArgsConstructor ： 注在类上，提供类的无参构造
@Setter ： 注在属性上，提供 set 方法
@Getter ： 注在属性上，提供 get 方法
@EqualsAndHashCode ： 注在类上，提供对应的 equals 和 hashCode 方法
@Log4j/@Slf4j ： 注在类上，提供对应的 Logger 对象，变量名为 log
```

在这里通过注入一个**@Data**注释提供了,get、set、equals、hasCode、canEqual、toString等方法



springboot中的层次划分



Entity(pojo):实体层,**数据库中的类**

entity层是实体层,也就是所谓的modal层,也称为pojo层,是数据库在项目中的类,该文件包含实体类的属性和对应属性的set、get方法

DAO(mapper),持久层,主要与数据库进行交互

DAO层=mapper层，现在用Mybatis逆向工程生成的mapper层,其实就是dao层。DAO层会调用entity层，DAO中会定义实际使用到的方法,比如增删改查。DAO 层的数据源和数据库连接的参数都是在配置文件中进行配置的,配置文件一般在同层的XML文件夹中。数据持久化操作就是指，把数据放到持久化的介质中，同时提供增删改查操作。

**Service**层:业务层 控制业务

Service层主要负责业务模块的逻辑应用设计。先设计放接口的类，再创建实现的类，然后在配置文件中进行配置其实现的关联。service层调用dao层接口，接收dao层返回的数据，完成项目的基本功能设计。

pojo->mapper-service

Controller层:控制层,**控制业务逻辑**

Controller层负责具体的业务模块流程的控制

controller层负责前后端交互，接受前端请求，调用service层，接收service层返回的数据，最后返回具体的页面和数据到客户端。

Controller层像是一个服务员，他把客人（前端）点的菜（数据、请求的类型等）进行汇总什么口味、咸淡、量的多少，交给厨师长（Service层）

厨师长则告诉沾板厨师（Dao 1）、汤料房（Dao 2）、配菜厨师（Dao 3）等（统称Dao层）我需要什么样的半成品，副厨们（Dao层）就负责完成厨师长（Service）交代的任务。

具体的流程为

```java
controller--->service接口-->serviceImpl->dao接口--->daoImll--》mapper-->db
```

Controller层调用Service层的方法，Service层调用Dao层中的方法，其中调用的参数是使用Entity层进行传递的。

这样使得业务逻辑更加清晰,写代码更加方便,形成了层次



## 数据库设计的问题

#### bigint和int的区别

如果需要的范围比int大就用bigint

#### vachar格式

varchar用于存储字符串格式,例如存储某一篇文章

![image-20221205095135075](allPicture/image-20221205095135075.png)

在pojo当中bigint也就是`long`,varchar则对应着String类型

### 浅谈@RequestMapping @ResponseBody 和 @RequestBody 注解的用法与区别

#### 1.@RequestMapping

国际惯例先介绍什么是@RequestMapping，@RequestMapping 是一个用来处理请求地址映射的注解，可用于类或方法上。**用于类上，表示类中的所有响应请求的方法都是以该地址作为父路径**；**用于方法上，表示在类的父路径下追加方法上注解中的地址将会访问到该方法**，**此处需注意@RequestMapping用在类上可以没用，但是用在方法上必须有**。

**例如：**

```java
@Controller
//设置想要跳转的父路径
@RequestMapping(value = "/Controllers")
public class StatisticUserCtrl {
    //如需注入，则写入需要注入的类
    //@Autowired
 
            // 设置方法下的子路经
            @RequestMapping(value = "/method")
            public String helloworld() {
 
                return "helloWorld";
 
            }
}
```

**总的@RequestMapping来说,就是写请求路径,和前端对应,是请求的接口路径**

#### 2 @ResponseBody

@Responsebody 注解表示该方法的返回的结果直接写入 HTTP 响应正文（ResponseBody）中，一般在异步获取数据时使用，通常是在使用 @RequestMapping 后，返回值通常解析为跳转路径，加上 @Responsebody 后返回结果不会被解析为跳转路径，而是直接写入HTTP 响应正文中。

**使用时机：**
返回的数据不是html标签的页面，**而是其他某种格式的数据时（如json、xml等）使用；**

也就是说,通常在需要返回的是json时,使用ResponseBody

异步获取 json 数据，加上 @Responsebody 注解后，就会直接返回 json 数据。

**例如：**

```typescript
@RequestMapping(value = "user/login")
@ResponseBody
// 将ajax（datas）发出的请求写入 User 对象中,返回json对象响应回去
public User login(User user) {   
    User user = new User();
    user .setUserid(1);
    user .setUsername("MrF");
    user .setStatus("1");
    return user ;
}
```

#### 3 @RequestBody

@RequestBody 注解则是将 HTTP 请求正文插入方法中，使用适合的 HttpMessageConverter 将请求体写入某个对象。

**作用：**

> （1) 该注解用于读取Request请求的body部分数据，使用系统默认配置的HttpMessageConverter进行解析，然后把相应的数据绑定到要返回的对象上
>
> (2) 再把HttpMessageConverter返回的对象数据绑定到 controller中方法的参数上。

**使用时机：**

A) GET、POST方式提时， 根据request header Content-Type的值来判断:

> application/x-www-form-urlencoded， 可选（即非必须，因为这种情况的数据@RequestParam, @ModelAttribute也可以处理，当然@RequestBody也能处理）；
>
> multipart/form-data, 不能处理（即使用@RequestBody不能处理这种格式的数据）；
> 其他格式， 必须（其他格式包括application/json, application/xml等。这些格式的数据，必须使用@RequestBody来处理）；

**例如：**

```less
@RequestMapping(value = "user/login")
@ResponseBody
// 将ajax（datas）发出的请求写入 User 对象中
public User login(@RequestBody User user) {   
// 这样就不会再被解析为跳转路径，而是直接将user对象写入 HTTP 响应正文中
    return user;    
}
```

B) PUT方式提交时， 根据request header Content-Type的值来判断:

> application/x-www-form-urlencoded， 必须；multipart/form-data, 不能处理；其他格式， 必须；

说明：request的body部分的数据编码格式由header部分的Content-Type指定；

@requestBody与responseBoy的区别

##### 一、@RequestBody

@RequestBody的作用是将前端传来的json格式的数据转为自己定义好的javabean对象

如图以微信小程序为例，前端向后端传入如下json格式的数据

![img](https://pic1.zhimg.com/80/v2-8640cc56c3ef236bc94f1bcacf902a48_720w.webp)

需要注意的是传入数据的属性名称要和后端javabean中定义的一致

![img](https://pic4.zhimg.com/80/v2-627bbe9185e995f472eb2a9d7872f163_720w.webp)

发送请求后可以看到在控制台中我们通过javabean对象的get方法打印出了前端传来的值，说明json数据已经成功的被转换为了javabean对象，将对应的属性进行了赋值

![img](https://pic1.zhimg.com/80/v2-7f663aacbbc03e49a568ad8987d9cd10_720w.webp)

**注：@RequestBody要写在方法的参数前，不能写在方法名上方**

![img](https://pic2.zhimg.com/80/v2-8ebff740f406e2305bb65655fe65d9f1_720w.webp)



![image-20221205165819538](allPicture/image-20221205165819538.png)

##### 二、 @ResponseBody

@ResponseBody的作用是将后端以return返回的javabean类型数据转为json类型数据。在此就不做具体的事例演示

**注：@ResponseBody要写在方法名上**

![img](https://pic4.zhimg.com/80/v2-cf6860d13618f0b1931d5557f3a58fd3_720w.webp)

也就是说,**ResponseBody**就是将传过来的json转换为需要的javaBean,而 **RequestBody**就是将需要的数据为Json再传过去。

Vo的意思是视图,当某种表当中有一些数据,但是我们并不需要其中的全部时,就用vo

在这里,并不需要通过getRecord直接返回整张表的数据,因此通过vo取表需要的数据

ArticleVo在这里返回以下内容

需要的字段

```java
一篇文章需要id,因此返回id
文章里面有标题因此,返回title
 除此之外还有总结 summary字段 评论总数conmmentCounts 观看次数viewCounts,权重weight,创建时间 createDate,作者author,文章体:body 以及标签tags 和分类列表catergorys
```



不需要的字段

```java
下面是没用返回的 author_id body_id categoryid
```

简单来说,vo就是去除掉不需要的字段,加入需要的字段

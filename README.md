# springboot_dubboParent
# interfaceApi 公共的实体类 以及接口
# myclass_service 班级服务 application.yml 可以修改zookeeper地址 班级数据库地址 注册在服务注册中心上（zookeep）通过@Service
@DubboComponentScan("com.dubbo.myclass_service.service.impl")
# student_service 学生服务 application.yml 可以修改zookeeper地址 学生数据库地址 注册在服务注册中心上（zookeep）通过@Service
# stu_web 对外表现层 Controller 静态资源等 在服务注册中心中发现服务（zookeep） 通过@Reference注解发现

# 数据库文件名就是数据库名




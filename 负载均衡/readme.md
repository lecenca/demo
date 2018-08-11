编写配置文件 rptest02.conf 如下
```
events {
  worker_connections  1024;  ## Default: 1024
}

http{
    upstream backend {
        server 127.0.0.1:8081  weight=1;
        server 127.0.0.1:8082  weight=1;
    }
    server {
        listen 8080; 

        location / {
            proxy_pass http://backend;
        }
    }
}
```
生成 spring-boot 应用 loadblancingtry-ver-8081.jar 和 loadblancingtry-ver-8082.jar，分别绑定端口8081和8082，部署并运行。

运行nginx，输入命令
```
nginx -c /你自己的路径/rptest02.conf
```

在浏览器访问 你服务器的ip:8080 即可看到效果。
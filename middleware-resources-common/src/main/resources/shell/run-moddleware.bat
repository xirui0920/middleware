@echo off
D:
cd \
cd D:\middleware
start javaw -jar -Xms3072m -Xmx3072m -XX:MaxPermSize=768m -XX:+UseParallelGC middleware.war --spring.profiles.active=prod
exit
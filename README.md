# public-services
Моё первое приложение, где я реализовывал frontend и backand.

Для заполнения базы данных можете использовать фаил fillDb.js в /public-services/src/main/resources/fillDb.js
Если у Вас не установлена mongo или не хотите засорять свою базу, можете воспользоваться Docker. Для этого:
1) загрузите себе официальный docker-images (docker pull mongo)
2) запустите его с параметрами: docker run -d -p 27017:27017 mongo
3) загрузите из /public-services/src/main/resources/fillDb.js данные в базу. Для этого в консоли выполните команды: 
- $ mongo
- $ load("/public-services/src/main/resources/fillDb.js") (путь укажите фактический)


После этого можете просто запустить проект в IDE или запустить jar-фаил (java -jar /*имя файла*/)


# Healthy AI - Boltech

## Cómo ejecutar con Docker



#### Para realizar los siguientes pasos es importante que tengas docker iniciado.

Primero clonamos la rama dockerLocal.
Luego tenemos que modificar el archivo backend/src/main/resources/config.properties y donde dice CAMBIAR poner tanto la clave de OpenAI y la de Llama 2 que hay en el documento pdf.
Una vez hecho esto tendremos que volver a crear el jar del backend. Para ello abris la carpeta de backend con visual studio y en la parte inferior izquierda pinchas donde dice MAVEN luego en boltech y luego en lifecycle, aparecerán varios apartados pero solo tenéis que pinchar en el que dice package.
Una vez hecho eso se habrá generado el nuevo jar y tendréis que poner este comando desde la carpeta backend donde se encuentra el dockerfile:
```
docker build -t backend-app .
```
Ahora hacemos lo mismo para el front desde la carpeta frontend donde se encuentra el dockerfile:
```
docker build -t frontend-app .
```
Una vez hecho eso, debemos crear la red para que el servidor y el cliente se comuniquen
```
docker network create boltech
```
Ahora que tenemos la red creada ya podemos ejecutar los 2 contenedores, para ello tenéis que poner este comando para el front:
```
docker run --name "boltech-frontend" --network boltech -p 3000:8080 frontend-app
```
Y este otro para el back:
```
docker run --name "boltech-backend" --network boltech -p 3001:8081 backend-app
```

Con esto ya lo tendréis funcionando perfectamente en localhost:3000.

# Despliegue remoto:
https://boltech-first-aid.fly.dev/
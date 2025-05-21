FROM nginx:alpine

# Copia el index.html al directorio donde nginx sirve el contenido
COPY index.html /usr/share/nginx/html/index.html

COPY . /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]

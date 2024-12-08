FROM node:18.17.1-alpine AS build

# Installer Git
#RUN apk update && apk add git

# Copier le projet vers le dossier de travail ps-front
COPY . /ps-front
WORKDIR ps-front

# Prepare
RUN npm install -g @angular/cli@16.2.0
RUN npm install
RUN npm run buildProd

FROM nginx:alpine

COPY --from=build /ps-front/dist/portail /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]

# Test build docker :
# docker build . -t ps_front:latest --progress plain

# Run :
# docker run -it -p 80:80 ps_front:latest


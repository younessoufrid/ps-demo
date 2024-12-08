FROM node:18.17.1-alpine AS build

ARG FRONT_BRANCH="dev"

# Installer Git
#RUN apk update && apk add git

RUN apk update && apk add git

# Prepare
RUN git clone https://github.com/younessoufrid/ps-demo.git -b ${FRONT_BRANCH}
WORKDIR ps-demo/ui/

# dependencies
RUN npm install -g @angular/cli@16.2.0
RUN npm install
RUN npm run buildProd

FROM nginx:alpine

COPY --from=build /ps-demo/ui/dist /usr/share/nginx/html
COPY ./nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]

# Test build docker :
# docker build . -t ps_front:latest --progress plain

# Run :
# docker run -it -p 80:80 ps_front:latest

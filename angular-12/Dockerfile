FROM node:12.16.1-alpine

RUN npm install -g @angular/cli@12.0

WORKDIR /app

COPY ./package.json ./package-lock.json ./
RUN npm install

COPY . .

ENTRYPOINT ["npm", "run", "prod"]

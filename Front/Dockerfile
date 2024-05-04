FROM node:alpine AS build

WORKDIR /app

COPY package*.json ./

RUN npm install

RUN npx ngcc --properties es2023 browser main --first-only --create-ivy-entry-points

COPY . .

RUN npm run build

FROM nginx:stable

COPY --from=build /app/dist/front /usr/share/ngnix/html

EXPOSE 80


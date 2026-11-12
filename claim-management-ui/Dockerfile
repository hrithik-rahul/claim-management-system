
# Stage 1: Build Angular application
FROM node:24-alpine AS build

WORKDIR /app

COPY package*.json ./

RUN npm ci

COPY . .

RUN npm run build

# Stage 2: Serve Angular using NGINX
FROM nginx:alpine

# Copy Angular production build
COPY --from=build /app/dist/claim-management-ui/browser /usr/share/nginx/html

# Copy custom NGINX configuration
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]

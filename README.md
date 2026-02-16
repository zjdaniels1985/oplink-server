# oplink-server
Operations Link Server

## Overview

Oplink Server is a comprehensive communication platform built with Spring Boot 3, featuring:

- **User Authentication & Authorization**: Secure registration and login with role-based access control (USER, ADMIN)
- **Server Management**: Create and join communication servers
- **Channel System**: Organize conversations within servers
- **Real-time Messaging**: WebSocket-based chat with STOMP protocol
- **Voice/Video Support**: WebRTC signaling stubs and TURN server integration (placeholders for SFU)
- **Web UI**: Thymeleaf-based responsive interface
- **Docker Support**: Containerized deployment with PostgreSQL and Redis

## Tech Stack

- **Backend**: Spring Boot 3.2.2 (Java 17)
- **Database**: PostgreSQL 16
- **Session Store**: Redis 7
- **Security**: Spring Security with BCrypt password encoding
- **Messaging**: WebSocket/STOMP for real-time communication
- **Templates**: Thymeleaf with Spring Security integration
- **Database Migration**: Flyway
- **Build Tool**: Maven
- **Containerization**: Docker & Docker Compose

## Prerequisites

- Docker and Docker Compose installed
- Java 17 (for local development)
- Maven 3.9+ (for local development)

## Quick Start with Docker

1. **Clone the repository**:
   ```bash
   git clone https://github.com/zjdaniels1985/oplink-server.git
   cd oplink-server
   ```

2. **Copy environment file**:
   ```bash
   cp .env.example .env
   ```

3. **Edit `.env` file** (optional):
   Customize database credentials, Redis settings, and TURN server configuration as needed.

4. **Start all services**:
   ```bash
   docker-compose up --build -d
   ```

5. **Access the application**:
   - Web UI: http://localhost:8080
   - API: http://localhost:8080/api

6. **View logs**:
   ```bash
   docker-compose logs -f app
   ```

7. **Stop all services**:
   ```bash
   docker-compose down
   ```

## Local Development Setup

### 1. Start PostgreSQL and Redis

```bash
docker-compose up -d postgres redis
```

### 2. Build the application

```bash
mvn clean package -DskipTests
```

### 3. Run the application

```bash
java -jar target/oplink-server-0.0.1-SNAPSHOT.jar
```

Or use Maven:

```bash
mvn spring-boot:run
```

## Environment Variables

The application uses the following environment variables (see `.env.example`):

### Database Configuration
- `DB_NAME`: PostgreSQL database name (default: `oplink`)
- `DB_USER`: Database username (default: `oplink`)
- `DB_PASSWORD`: Database password (default: `oplink`)
- `DB_PORT`: Database port (default: `5432`)
- `DB_HOST`: Database host (default: `localhost`)

### Redis Configuration
- `REDIS_HOST`: Redis host (default: `localhost`)
- `REDIS_PORT`: Redis port (default: `6379`)
- `REDIS_PASSWORD`: Redis password (default: empty)

### Server Configuration
- `SERVER_PORT`: Application port (default: `8080`)
- `ADMIN_PASSWORD`: Default admin password (default: `admin`)

### CORS Configuration
- `CORS_ORIGINS`: Allowed origins for CORS (default: `http://localhost:3000,http://localhost:8080`)

### TURN Server Configuration
- `TURN_URLS`: TURN server URLs (placeholder)
- `TURN_USERNAME`: TURN server username (placeholder)
- `TURN_CREDENTIAL`: TURN server credential (placeholder)

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Login (returns user info)
- `GET /api/auth/me` - Get current user info

### Servers
- `GET /api/servers` - List user's servers
- `GET /api/servers/all` - List all available servers
- `POST /api/servers` - Create a new server
- `GET /api/servers/{serverId}` - Get server details
- `POST /api/servers/{serverId}/join` - Join a server

### Channels
- `GET /api/servers/{serverId}/channels` - List channels in a server

### Messages
- `GET /api/channels/{channelId}/messages` - Get messages (with pagination)
- `POST /api/channels/{channelId}/messages` - Send a message

### Voice/WebRTC (Signaling Stubs)
- `POST /api/voice/{channelId}/offer` - Handle SDP offer
- `POST /api/voice/{channelId}/answer` - Handle SDP answer
- `POST /api/voice/{channelId}/ice` - Handle ICE candidate
- `GET /api/voice/turn-credentials` - Get TURN server credentials

## WebSocket Endpoints

### STOMP Configuration
- **Endpoint**: `/ws`
- **App Prefix**: `/app`
- **Topic Prefix**: `/topic`, `/queue`

### Topics
- `/topic/channels/{channelId}/messages` - Real-time messages
- `/topic/servers/{serverId}/presence` - User presence (stub)

## Web Pages

- `/` - Home (redirects to servers)
- `/login` - Login page
- `/register` - Registration page
- `/profile` - User profile
- `/servers` - Server listing and management
- `/servers/{serverId}` - Server detail view
- `/admin` - Admin dashboard (requires ADMIN role)

## Database Schema

The application uses Flyway for database migrations. The initial schema includes:

- **roles**: User roles (ROLE_USER, ROLE_ADMIN)
- **users**: User accounts
- **user_roles**: User-role mapping
- **servers**: Communication servers
- **server_memberships**: Server membership
- **channels**: Communication channels
- **messages**: Chat messages

## Default Roles

The database is seeded with two default roles:
- `ROLE_USER`: Standard user role (assigned to all new registrations)
- `ROLE_ADMIN`: Administrator role (full access)

## Security Features

- **Password Encryption**: BCrypt password hashing
- **Session Management**: Redis-backed sessions with 30-minute timeout
- **CSRF Protection**: Enabled for form submissions
- **CORS**: Configurable cross-origin resource sharing
- **Role-based Access Control**: USER and ADMIN roles
- **Form Login**: Traditional form-based authentication for web UI
- **REST API Authentication**: Session-based or token-friendly

## Development Notes

- **Open-in-view**: Disabled for better performance
- **JPA DDL**: Set to `validate` (Flyway handles migrations)
- **Logging**: DEBUG level for application code
- **WebSocket**: SockJS support enabled for browser compatibility

## Production Deployment

For production deployment:

1. Update environment variables in `.env`:
   - Use strong passwords for database and admin account
   - Configure actual TURN server credentials
   - Set appropriate CORS origins
   - Use Redis password

2. Enable SSL/TLS (configure reverse proxy like Nginx)

3. Set appropriate logging levels in `application.yml`

4. Configure backup strategy for PostgreSQL

5. Monitor application logs and metrics

## Troubleshooting

### Database Connection Issues
```bash
docker-compose logs postgres
```

### Redis Connection Issues
```bash
docker-compose logs redis
```

### Application Issues
```bash
docker-compose logs app
```

### Reset Database
```bash
docker-compose down -v
docker-compose up -d
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License.

## Support

For issues and questions, please open an issue on GitHub.


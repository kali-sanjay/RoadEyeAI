# RoadEye AI

RoadEye AI is a Spring Boot application for reviewing road hazard images with
the NVIDIA AI API.

## Requirements

- Java 25
- MySQL with a `roadeeye_db` database
- An NVIDIA API key

## Configuration

Set these environment variables before starting the application:

| Variable | Required | Default |
| --- | --- | --- |
| `DB_URL` | No | `jdbc:mysql://localhost:3306/roadeeye_db` |
| `DB_USERNAME` | No | `root` |
| `DB_PASSWORD` | Yes | None |
| `NVIDIA_API_KEY` | Yes | None |

In PowerShell, for example:

```powershell
$env:DB_PASSWORD = "your-database-password"
$env:NVIDIA_API_KEY = "your-nvidia-api-key"
.\mvnw.cmd spring-boot:run
```

Never commit API keys, database passwords, or other credentials to the
repository. Configure them as environment variables or host-managed secrets.

## Deploying on Vercel

Vercel can run this app using its beta Container Runtime and the root
`Dockerfile`. Import this repository into Vercel and select the Container
framework if it is not detected automatically.

Configure these environment variables in the Vercel project settings:

- `DB_URL`: connection URL for an externally hosted, publicly reachable MySQL
  database
- `DB_USERNAME`: database username
- `DB_PASSWORD`: database password
- `NVIDIA_API_KEY`: NVIDIA API key

Vercel does not provide this app's MySQL database. The database host must allow
connections from the deployed app. The app listens on Vercel's `PORT` variable.

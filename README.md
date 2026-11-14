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

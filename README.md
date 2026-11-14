# RoadEye AI

RoadEye AI is a Spring Boot application for reviewing road hazard images with
the NVIDIA AI API.

## Requirements

- Java 25
- An NVIDIA API key

## Configuration

Set these environment variables before starting the application:

| Variable | Required | Default |
| --- | --- | --- |
| `NVIDIA_API_KEY` | Yes | None |

In PowerShell, for example:

```powershell
$env:NVIDIA_API_KEY = "your-nvidia-api-key"
.\mvnw.cmd spring-boot:run
```

Never commit API keys, database passwords, or other credentials to the
repository. Configure them as environment variables or host-managed secrets.

## Deploying on Vercel

Vercel can run this app using its beta Container Runtime and the root
`Dockerfile`. Import this repository into Vercel and select the Container
framework if it is not detected automatically.

Configure `NVIDIA_API_KEY` in the Vercel project settings. The app does not
currently persist reports or require a database. It listens on Vercel's `PORT`
variable.

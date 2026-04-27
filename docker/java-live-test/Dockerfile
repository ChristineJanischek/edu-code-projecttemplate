FROM eclipse-temurin:21-jdk-jammy

WORKDIR /workspace

RUN apt-get update \
    && apt-get install -y --no-install-recommends inotify-tools xvfb \
    && rm -rf /var/lib/apt/lists/*

COPY src/volleyball ./src/volleyball
COPY scripts ./scripts

RUN chmod +x ./scripts/*.sh \
    && ./scripts/test-java.sh

CMD ["bash", "./scripts/java-live-test-runner.sh", "watch"]
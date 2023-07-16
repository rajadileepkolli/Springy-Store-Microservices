FROM gitpod/workspace-full

RUN bash -c ". /home/gitpod/.sdkman/bin/sdkman-init.sh \
              && sdk update && sdk list java && sdk install java 20.0.1-amzn && sdk default java 20.0.1-amzn"
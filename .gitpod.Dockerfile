FROM gitpod/workspace-full

RUN bash -c ". /home/gitpod/.sdkman/bin/sdkman-init.sh \
              && sdk update && sdk list java && sdk install java 11.0.8-amzn && sdk install java 15.0.0-amzn && sdk default java 15.0.0-amzn"

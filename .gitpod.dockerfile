FROM gitpod/workspace-full-vnc

RUN bash -c ". /home/gitpod/.sdkman/bin/sdkman-init.sh \
             && sdk install java 15-open \
             && sdk default java 15-open"

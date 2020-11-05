FROM gitpod/workspace-full

USER gitpod

# Install custom tools, runtime, etc. using apt-get
# For example, the command below would install "bastet" - a command line tetris clone:
#
# RUN sudo apt-get -q update && \
#     sudo apt-get install -yq bastet && \
#     sudo rm -rf /var/lib/apt/lists/*
#
# More information: https://www.gitpod.io/docs/config-docker/

RUN bash -c ". /home/gitpod/.sdkman/bin/sdkman-init.sh \
             && sdk upgrade \
             && sdk install java 8.0.265-open \
             && sdk uninstall java 11.0.6.fx-zulu"

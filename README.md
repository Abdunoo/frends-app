# folder prod to upload
 abdun.upload-folder=/root/abdun/upload/myapp/
 abdun.story-folder=/root/abdun/story/myapp/
 abdun.product-folder=/root/abdun/product/myapp/

# folder dev to upload
abdun.upload-folder=/home/abdun/experiment/upload/myapp/
abdun.story-folder=/home/abdun/experiment/story/myapp/
abdun.product-folder=/home/abdun/experiment/product/myapp/

# upload quarkus
export GRAALVM_HOME=/home/app/mandrel-java17-23.0.1.2-Final/
quarkus build --native;
cd target/
tar -zcvf ./myQuarkus-1.0.0-SNAPSHOT-runner.tar.gz myQuarkus-1.0.0-SNAPSHOT-runner 
scp ./myQuarkus-1.0.0-SNAPSHOT-runner.tar.gz root@135.181.41.212:/root/myApp/app-quarkus/
tar -xzvf myQuarkus-1.0.0-SNAPSHOT-runner.tar.gz

# upload web
change API on enironments.ts
ng build
cd /dist
tar -zcvf ./ig-web.tar.gz ./
scp ./ig-web.tar.gz  root@135.181.41.212:/opt/
tar -xzvf ig-web.tar.gz

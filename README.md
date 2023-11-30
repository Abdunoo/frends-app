# folder prod to upload
 abdun.upload-folder=your folder
 abdun.story-folder=your folder
 abdun.product-folder= your folder

# folder dev to upload
abdun.upload-folder=your folder
abdun.story-folder=your folder
abdun.product-folder=your folder

# upload quarkus
export GRAALVM_HOME=/home/app/mandrel-java17-23.0.1.2-Final/
quarkus build --native;
cd target/
tar -zcvf ./myQuarkus-1.0.0-SNAPSHOT-runner.tar.gz myQuarkus-1.0.0-SNAPSHOT-runner 
scp ./myQuarkus-1.0.0-SNAPSHOT-runner.tar.gz root@yourIPserver:/root/myApp/app-quarkus/
tar -xzvf myQuarkus-1.0.0-SNAPSHOT-runner.tar.gz

# upload web
change API on enironments.ts
ng build
cd /dist
tar -zcvf ./ig-web.tar.gz ./
scp ./ig-web.tar.gz  root@yourIPserver:/opt/
tar -xzvf ig-web.tar.gz

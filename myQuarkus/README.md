# folder prod to upload
 abdun.upload-folder=/root/abdun/upload/myapp/
 abdun.story-folder=/root/abdun/story/myapp/
 abdun.product-folder=/root/abdun/product/myapp/

# folder dev to upload
abdun.upload-folder=/home/abdun/experiment/upload/myapp/
abdun.story-folder=/home/abdun/experiment/story/myapp/
abdun.product-folder=/home/abdun/experiment/product/myapp/

# upload quarkus
quarkus build --native;
cd target/
tar -zcvf ./myQuarkus-1.0.0-SNAPSHOT-runner.tar.gz myQuarkus-1.0.0-SNAPSHOT-runner 
scp ./myQuarkus-1.0.0-SNAPSHOT-runner.tar.gz root@135.181.41.212:/root/myApp/app-quarkus/

# upload web
tar -zcvf ./ig-web.tar.gz ./
scp ./ig-web.tar.gz  root@135.181.41.212:/opt/

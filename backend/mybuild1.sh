# react project build
cd ../frontend
npm run build

# index.html, main.js 복사(이동) : dist -> static
cd ../backend
rm -rf src/main/resources/static
rm -rf build
mv ../frontend/dist src/main/resources/static


# spring project build
./gradlew bootJar

# build image
docker build -t gaeyoung/prj3 .
docker push gaeyoung/prj3


# 컨테이너 멈추고
ssh -i src/main/resources/secret/key0527.pem ubuntu@15.164.231.30 'docker stop saengjoncoding'
# 컨테이너 삭제
ssh -i src/main/resources/secret/key0527.pem ubuntu@15.164.231.30 'docker rm saengjoncoding'
# pull image
ssh -i src/main/resources/secret/key0527.pem ubuntu@15.164.231.30 'docker pull gaeyoung/prj3'
# 컨테이너 실행
ssh -i src/main/resources/secret/key0527.pem ubuntu@15.164.231.30 'docker run -d -p 8080:8080 --restart always --name saengjoncoding gaeyoung/prj3'


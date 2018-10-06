FROM java:8
VOLUME /tmp
ADD public-services-0.0.1-SNAPSHOT.jar app.jar
RUN bash -c 'touch |app.jar'
ENTRYPOINT ["java", "-Djava.security.egd=file:|dev|.|urandom", "|app.java"]
# Pull image docker automatically
docker run -d --name kibana -p 5601:5601 --link elasticsearch:kibana -e "ELASTICSEARCH_URL=http://kibana:9200" docker.elastic.co/kibana/kibana:6.5.0

NOTE: This assignment has been compiled with Java11.

1. To Pull docker image use below command:
docker pull nainsumit/search-products:latest

2. Run Docker image:
docker run -p 8084:8084 search-products

3. Use Browser to search products using url:
localhost:8084/product?type=test&city=stockholm&&min_price=0&max_price=100&property=color&property:color=grey&property:gb_limit_min=0&&property:gb_limit_max=1000

4. Or use command line:
curl -X GET 'localhost:8084/product?type=test&city=stockholm&min_price=0&max_price=100&property=color&property:color=grey&property:gb_limit_min=0&property:gb_limit_max=1000'

Please change parameter values as example results will be empty

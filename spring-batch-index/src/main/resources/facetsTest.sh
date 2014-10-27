curl -X POST "http://localhost:9200/quicky/gare/_search?pretty=true" -d '
  {
      "query" : { "match" : {"pays" : "FRANCE"}},
      "aggs" : {
      	"group_by_ville" : { "terms" : { "field" : "ville.raw" } }
	}
 }
'

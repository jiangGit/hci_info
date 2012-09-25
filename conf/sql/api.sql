/* api.keys */
select api_key from api where state = 1

/*api.update*/
update api set note = @note where id = @id

/*api.remove*/
 update api set state = 0 where id = @id
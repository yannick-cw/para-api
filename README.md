## The api for the para service

This is the api for the paragliding secondhand service.
With this api new tags can be set for a user, the current tags can be retrieved and all
tags for all users can be accessed.

### Endpoints

#### GET tags for user `/tags/email`
Result body:
```javascript
{
    email: "123@test.com",
    terms: [
        "some",
        "tags",
        "are",
        "these"
        ]
}
```
#### POST tags for user `tags/email/tag1,tag2,tag3,tag4`
#### GET all users and tags `all-tags`
## The api for the para service

![build](https://travis-ci.org/yannick-cw/para-api.svg?branch=master)

This is the api for the paragliding secondhand service.
With this api new tags can be set for a user, the current tags can be retrieved and all
tags for all users can be accessed.

### Endpoints

#### GET tags for user `/tags/email`
Result body:
```javascript
{
    email: "123@test.com",
    tags: [
        "some",
        "tags",
        "are",
        "these"
        ]
}
```
#### POST tags for user `tags/email?tags=tag1,tag2,tag3,tag4`
#### GET all users and tags `all-tags`
Result body:
```javascript
{
    hallo@not.com: [
        "some,search,terms"
    ],
    yes@test.com: [
        "what,is,this"
    ]
}
```
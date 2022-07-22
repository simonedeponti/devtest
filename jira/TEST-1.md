## BUG

When I try to select the third page, I get no results.

## Setps to reproduce

Call http://localhost:8080/employees/ and see we get 3 pages:

    {
        "items": [...],
        "pageSize": 10,
        "pageNumber": 0,
        "totalPages": 3,
        "totalItems": 21
    }

But when I go to the third page via http://localhost:8080/employees/?page=2 I get

    {
        "items": [],
        "pageSize": 10,
        "pageNumber": 2,
        "totalPages": 3,
        "totalItems": 21
    }

## Expected result

`items` should not be an empty list
//
// $Id: Simple-1.tesc,v 1.1 1999-02-11 09:21:47 swtech20 Exp $

var (v1)
event (e1,e2)

and A
    or A1
        basic D
        from D to D on e1 do e2
        default D
    end A1
    or A2
        basic D
        from D to D on [v1] do e1
        default D
    end A2
end A
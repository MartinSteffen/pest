//
// $Id: Simple-2.tesc,v 1.3 1999-02-11 09:21:47 swtech20 Exp $

var (v1,v2)
event (e1,e2)

or A
    basic B1
    basic B2
    from B1 to B2 on e3 <=> e2 => e2 | e1
    from B2 to B1 on !e1 & e2[!v1] do (e1,v1=true,v2=false)
    from B2 to B2 on [v1]
    default B1
end A

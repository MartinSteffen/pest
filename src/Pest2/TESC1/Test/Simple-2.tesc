var (v1,v2)
event (e1,e2)

or A
    basic B1
    basic B2
    from B1 to B2 on e3 <=> e2 => e2 | e1
    from B2 to B1 on !e1 & e2[!v3] do (e1,v4=true,e1=false)
    from B2 to B2 on [v1]
    default B1
end A

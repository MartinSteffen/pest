var v1
event (e1,e2)

or A
      basic B1
      basic B2
      from B1 to B2 on (e1 <=> e2 => e2)
      from B2 to B1 on !v1 & v1 | !e1 & e2 do (e1,v1=true)
      default B1
end A
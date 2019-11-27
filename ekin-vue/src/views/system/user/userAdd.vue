<template>
  <user-form :is-edit="false" />
</template>

<script>
import userForm from './userForm'

export default {
  name: 'AddUser',
  components: { userForm }
}
</script>
